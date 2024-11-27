package org.example.datn.service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.example.datn.config.VNPayConfig;
import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.HoaDon;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.utils.VNPayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPayConfig vnPayConfig;
    private final RestTemplate restTemplate;
    @Value("https://sandbox.vnpayment.vn/merchant_webapi/api/transaction")
    private String vnpApiUrl;

    @Value("0H79HB1UYO8YY4D512RQ1RHE1PF4TWHI")
    private String secretKey;

    @Value("D08QML65")
    private String tmnCode;

    public String createVnPayPayment(HoaDon hoaDon, String bankCode, String ipAddress) {
        long amount = hoaDon.getTongTien().longValue() * 100L;
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(hoaDon);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", ipAddress);
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return paymentUrl;
    }

    public ServiceResult refundTransaction(String ma, Long amount, String ipAddress, UserAuthentication ua) throws IOException {
        String vnpRequestId = VNPayUtil.getRandomNumber(8);
        String vnpVersion = "2.1.0";
        String vnpCommand = "refund";
        String vnpTransactionType = "02";
        String vnpTxnRef = String.valueOf(ma);
        String vnpAmount = String.valueOf(amount * 100);
        String vnpOrderInfo = "Hoan tien don hang:" + ma;
        String vnpTransactionNo = "";
        String vnpCreateBy = String.valueOf(ua.getPrincipal());

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        String vnpTransactionDate = formatter.format(calendar.getTime());

        JsonObject vnpParams = new JsonObject();
        vnpParams.addProperty("vnp_RequestId", vnpRequestId);
        vnpParams.addProperty("vnp_Version", vnpVersion);
        vnpParams.addProperty("vnp_Command", vnpCommand);
        vnpParams.addProperty("vnp_TmnCode", tmnCode);
        vnpParams.addProperty("vnp_TransactionType", vnpTransactionType);
        vnpParams.addProperty("vnp_TxnRef", vnpTxnRef);
        vnpParams.addProperty("vnp_Amount", vnpAmount);
        vnpParams.addProperty("vnp_OrderInfo", vnpOrderInfo);
        vnpParams.addProperty("vnp_TransactionDate", vnpTransactionDate);
        vnpParams.addProperty("vnp_CreateBy", vnpCreateBy);
        vnpParams.addProperty("vnp_CreateDate", vnpCreateDate);
        vnpParams.addProperty("vnp_IpAddr", ipAddress);

        // Tạo hash_secure
        String hashData = String.join("|", vnpRequestId, vnpVersion, vnpCommand, tmnCode, vnpTransactionType, vnpTxnRef, vnpAmount, vnpTransactionNo, vnpTransactionDate, vnpCreateBy, vnpCreateDate, ipAddress, vnpOrderInfo);
        String vnpSecureHash = VNPayUtil.hmacSHA512(secretKey, hashData);

        vnpParams.addProperty("vnp_SecureHash", vnpSecureHash);

        // Gửi yêu cầu API đến VNPay
        URL url = new URL(vnpApiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnpParams.toString());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();
        String output;
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        if (responseCode == 200) {
            return new ServiceResult();
        } else {
            return new ServiceResult(SystemConstant.STATUS_FAIL, SystemConstant.CODE_400);
        }
    }


}

package org.example.datn.controller;

import lombok.RequiredArgsConstructor;
import org.example.datn.entity.HoaDonChiTiet;
import org.example.datn.entity.ThanhToan;
import org.example.datn.model.enums.StatusHoaDon;
import org.example.datn.model.enums.StatusThanhToan;

import org.example.datn.service.HoaDonChiTietService;
import org.example.datn.service.HoaDonService;
import org.example.datn.service.ThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private ThanhToanService thanhToanService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("/vn-pay-callback")
    @Transactional(rollbackOn = Exception.class)
    public String payCallbackHandler(HttpServletRequest request) {
        Map<String, String[]> paramsMap = request.getParameterMap();
        Map<String, String> vnpParams = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            vnpParams.put(entry.getKey(), entry.getValue()[0]);
        }
        String vnpResponseCode = vnpParams.get("vnp_ResponseCode");
        if ("00".equals(vnpResponseCode)) {
            try {
                String orderId = vnpParams.get("vnp_TxnRef");
                var hoaDon = hoaDonService.findByMa(orderId)
                        .orElseThrow(() -> new EntityNotFoundException("HoaDon not found"));
                hoaDon.setTrangThai(StatusHoaDon.CHO_XAC_NHAN.getValue());
                hoaDon.setNgayThanhToan(LocalDateTime.now());
                hoaDonService.save(hoaDon);

                List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.findByIdHoaDon(hoaDon.getId());
                hoaDonChiTietList.forEach(e -> e.setTrangThai(StatusHoaDon.CHO_XAC_NHAN.getValue()));
                hoaDonChiTietService.saveAll(hoaDonChiTietList);

                ThanhToan thanhToan = thanhToanService.findByIdHoaDon(hoaDon.getId())
                        .orElseThrow(() -> new EntityNotFoundException("ThanhToan not found"));
                thanhToan.setTrangThai(StatusThanhToan.DA_THANH_TOAN.getValue());
                thanhToan.setNgayThanhToan(LocalDateTime.now());
                thanhToanService.save(thanhToan);

                return "redirect:/bill";
            } catch (Exception e) {
                return "Lỗi khi xử lý callback từ VNPay: " + e.getMessage();
            }
        } else {
            return "Thanh toán thất bại, mã lỗi: " + vnpResponseCode;
        }
    }

}

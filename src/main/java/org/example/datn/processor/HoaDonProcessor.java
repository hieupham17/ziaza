package org.example.datn.processor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.*;
import org.example.datn.exception.*;
import org.example.datn.jwt.JwtGenerator;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.enums.*;
import org.example.datn.model.request.*;
import org.example.datn.model.response.*;
import org.example.datn.processor.auth.AuthenticationChannelProvider;
import org.example.datn.processor.auth.AuthoritiesValidator;
import org.example.datn.service.*;
import org.example.datn.transformer.*;
import org.example.datn.utils.VNPayUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.example.datn.utils.CalendarUtil.DateTimeUtils.now;

/**
 * @author hoangKhong
 */
@Component
public class HoaDonProcessor {
    @Autowired
    HoaDonService service;

    @Autowired
    HoaDonTransformer hoaDonTransformer;

    @Autowired
    DiaChiGiaoHangProcessor diaChiGiaoHangProcessor;

    @Autowired
    PhuongThucVanChuyenProcessor phuongThucVanChuyenProcessor;

    @Autowired
    UserProcessor userProcessor;

    @Autowired
    GioHangChiTietService gioHangChiTietService;

    @Autowired
    GioHangService gioHangService;

    @Autowired
    HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    PhuongThucThanhToanService phuongThucThanhToanService;

    @Autowired
    ThanhToanService thanhToanService;
    @Autowired
    HoaDonChiTietTransformer hoaDonChiTietTransformer;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;
    @Autowired
    private SanPhamChiTietTransformer sanPhamChiTietTransformer;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamTransformer sanPhamTransformer;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private SizeService sizeService;

    public ServiceResult getAll() {
        var list = service.getAll();
        var models = list.stream().map(hoaDon -> {
            var model = toModel(hoaDon);
            var diaChiGiaoHang = diaChiGiaoHangProcessor.findById(hoaDon.getIdDiaChiGiaoHang());
            var phuongThucVanChuyen = phuongThucVanChuyenProcessor.findById(hoaDon.getIdPhuongThucVanChuyen());
            var user = userProcessor.findById(hoaDon.getIdNguoiDung());
            model.setUserModel(user);
            model.setDiaChiGiaoHangModel(diaChiGiaoHang);
            model.setPhuongThucVanChuyenModel(phuongThucVanChuyen);
            return model;
        }).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }


    private HoaDonModel toModel(HoaDon hoaDon) {
        if (hoaDon == null) {
            return null;
        }

        HoaDonModel model = new HoaDonModel();
        model.setId(hoaDon.getId());
        model.setIdNguoiDung(hoaDon.getIdNguoiDung());
        model.setIdDiaChiGiaoHang(hoaDon.getIdDiaChiGiaoHang());
        model.setIdPhuongThucVanChuyen(hoaDon.getIdPhuongThucVanChuyen());
        model.setMa(hoaDon.getMa());
        model.setNgayDatHang(hoaDon.getNgayDatHang());
        model.setNgayThanhToan(hoaDon.getNgayThanhToan());
        model.setTongTien(hoaDon.getTongTien());
        model.setDiemSuDung(hoaDon.getDiemSuDung());
        model.setTrangThai(hoaDon.getTrangThai());
        model.setNgayCapNhat(hoaDon.getNgayCapNhat());
        return model;
    }

    public ServiceResult getById(Long id) {
        var hoaDon = service.findById(id).orElseThrow(() -> new EntityNotFoundException("hoaDon.not.found"));
        var model = hoaDonTransformer.toModel(hoaDon);
        var diaChiGiaoHang = diaChiGiaoHangProcessor.findById(hoaDon.getIdDiaChiGiaoHang());
        var phuongThucVanChuyen = phuongThucVanChuyenProcessor.findById(hoaDon.getIdPhuongThucVanChuyen());
        var user = userProcessor.findById(hoaDon.getIdDiaChiGiaoHang());
        model.setUserModel(user);
        model.setDiaChiGiaoHangModel(diaChiGiaoHang);
        model.setPhuongThucVanChuyenModel(phuongThucVanChuyen);
        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult save(HoaDonRequest request, UserAuthentication ua) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setIdNguoiDung(ua.getPrincipal());
        hoaDon.setIdDiaChiGiaoHang(request.getIdDiaChiGiaoHang());
        hoaDon.setIdPhuongThucVanChuyen(request.getIdPhuongThucVanChuyen());
        hoaDon.setMa(getRandomNumber(8));
        hoaDon.setDiemSuDung(0);
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setNguoiTao(ua.getPrincipal());
        hoaDon.setNguoiCapNhat(ua.getPrincipal());
        hoaDon.setNgayDatHang(LocalDateTime.now());
        service.save(hoaDon);

        var phuongThucThanhToan = phuongThucThanhToanService.findById(request.getIdPhuongThucThanhToan())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phương thức thanh toán"));

        var gioHangChiTiet = gioHangChiTietService.findByIdIn(request.getIdGioHangChiTiet());
        BigDecimal tongTien = BigDecimal.ZERO;
        for (GioHangChiTiet ghct : gioHangChiTiet) {
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setIdHoaDon(hoaDon.getId());
            hdct.setIdSanPhamChiTiet(ghct.getIdSanPhamChiTiet());
            hdct.setSoLuong(ghct.getSoLuong());
            hdct.setGia(ghct.getGia());
            BigDecimal giaTien = ghct.getGia().multiply(BigDecimal.valueOf(ghct.getSoLuong()));
            tongTien = tongTien.add(giaTien);
            hdct.setTrangThai(phuongThucThanhToan.getLoai().equals(TypeThanhToan.CASH) ? StatusHoaDon.CHO_XAC_NHAN.getValue() : StatusHoaDon.CHO_THANH_TOAN.getValue());
            hdct.setNgayTao(LocalDateTime.now());
            hdct.setNgayCapNhat(LocalDateTime.now());
            hdct.setNguoiTao(ua.getPrincipal());
            hdct.setNguoiCapNhat(ua.getPrincipal());
            hoaDonChiTietService.save(hdct);
            ghct.setTrangThai(StatusGioHang.DA_DAT_HANG.getValue());
            gioHangChiTietService.save(ghct);
        }

        if (request.getGiaTriVoucher() != null) {
            tongTien = tongTien.subtract(request.getGiaTriVoucher());
        }

        var phuongThucVanChuyen = phuongThucVanChuyenProcessor.get(request.getIdPhuongThucVanChuyen()).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phương thức vận chuyển"));
        tongTien = tongTien.add(phuongThucVanChuyen.getPhiVanChuyen());

        hoaDon.setTongTien(tongTien);
        hoaDon.setTrangThai(phuongThucThanhToan.getLoai().equals(TypeThanhToan.CASH) ? StatusHoaDon.CHO_XAC_NHAN.getValue() : StatusHoaDon.CHO_THANH_TOAN.getValue());
        service.save(hoaDon);
        ThanhToan thanhToan = new ThanhToan();
        thanhToan.setIdHoaDon(hoaDon.getId());
        thanhToan.setIdPhuongThucThanhToan(phuongThucThanhToan.getId());
        thanhToan.setSoTien(hoaDon.getTongTien());
        thanhToan.setTrangThai(phuongThucThanhToan.getLoai().equals(TypeThanhToan.CASH) ? StatusThanhToan.CHUA_THANH_TOAN.getValue() : StatusThanhToan.DANG_XU_LY.getValue());
        thanhToanService.save(thanhToan);
        if (phuongThucThanhToan.getLoai().equals(TypeThanhToan.VNPAY)) {
            HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddress = VNPayUtil.getIpAddress(httpRequest);
            String vnPayResponse = paymentService.createVnPayPayment(hoaDon, "NCB", ipAddress);
            return new ServiceResult(vnPayResponse, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
        }
        return new ServiceResult();
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult updateStatus(Long id, UserAuthentication ua) {
        // Tìm hóa đơn theo ID
        HoaDon hoaDon = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tồn tại"));

        // Xác định trạng thái mới dựa trên trạng thái hiện tại
        Integer newTrangThai;
        switch (hoaDon.getTrangThai()) {
            case 0:
            case 1:
                newTrangThai = StatusHoaDon.CHO_GIAO_HANG.getValue();
                break;
            case 2:
                newTrangThai = StatusHoaDon.VAN_CHUYEN.getValue();
                break;
            case 3:
                newTrangThai = StatusHoaDon.HOAN_THANH.getValue();
                hoaDon.setNgayThanhToan(LocalDateTime.now());
                break;
            default:
                throw new IllegalArgumentException("Trạng thái không hợp lệ để cập nhật");
        }
        // Cập nhật trạng thái hóa đơn
        hoaDon.setTrangThai(newTrangThai);
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setNguoiCapNhat(ua.getPrincipal());

        // Cập nhật trạng thái các chi tiết hóa đơn
        updateHoaDonChiTiet(id, newTrangThai);

        // Lưu hóa đơn
        service.save(hoaDon);

        // Trả về kết quả thành công
        return new ServiceResult("Hóa đơn đã được cập nhật trạng thái thành công",
                SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    // Cập nhật trạng thái cho các chi tiết hóa đơn
    private void updateHoaDonChiTiet(Long idHoaDon, Integer trangThai) {
        var hoaDonChiTietList = hoaDonChiTietService.findByIdHoaDon(idHoaDon);
        hoaDonChiTietList.forEach(e -> e.setTrangThai(trangThai));
        hoaDonChiTietService.saveAll(hoaDonChiTietList);
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult updateHuyHoaDon(Long id, UserAuthentication ua, String lyDoHuy) {
        // Tìm hóa đơn theo ID
        HoaDon hoaDon = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tồn tại"));

        // Xác định trạng thái mới dựa trên trạng thái hiện tại
        Integer newTrangThai;
        switch (hoaDon.getTrangThai()) {
            case 0:
            case 1:
                newTrangThai = StatusHoaDon.DA_HUY.getValue();
                break;
            case 2:
                newTrangThai = StatusHoaDon.DA_HUY.getValue();
                break;
            case 3:
                newTrangThai = StatusHoaDon.DA_HUY.getValue();
                break;
            default:
                throw new IllegalArgumentException("Trạng thái không hợp lệ để cập nhật");
        }
        // Cập nhật trạng thái hóa đơn
        hoaDon.setLyDoHuy(lyDoHuy);
        hoaDon.setTrangThai(newTrangThai);
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setNguoiCapNhat(ua.getPrincipal());

        // Cập nhật trạng thái các chi tiết hóa đơn
        updateHoaDonChiTiet(id, newTrangThai);
        cancelOrderDetails(hoaDon);
        // Lưu hóa đơn
        service.save(hoaDon);

        // Trả về kết quả thành công
        return new ServiceResult("Hóa đơn đã được cập nhật trạng thái thành công",
                SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }


    public ServiceResult getByTrangThai(Integer trangThai) {
        List<HoaDon> hoaDons = service.finByTrangThai(trangThai);
        List<HoaDonModel> models = hoaDons.stream().map(e -> {
            var model = hoaDonTransformer.toModel(e);
            var hoaDonChiTiets = hoaDonChiTietService.findByIdHoaDon(e.getId());
            var hoaDonChiTietModels = hoaDonChiTiets.stream().map(hoaDonChiTietTransformer::toModel).collect(Collectors.toList());
            var diaChiGiaoHangModel = diaChiGiaoHangProcessor.findById(e.getIdDiaChiGiaoHang());
            var phuongThucVanChuyenModel = phuongThucVanChuyenProcessor.findById(e.getIdPhuongThucVanChuyen());
            var userModel = userProcessor.findById(e.getIdDiaChiGiaoHang());
            model.setDiaChiGiaoHangModel(diaChiGiaoHangModel);
            model.setPhuongThucVanChuyenModel(phuongThucVanChuyenModel);
            model.setUserModel(userModel);
            model.setHoaDonChiTietModels(hoaDonChiTietModels);
            return model;
        }).collect(Collectors.toList());
        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    public ServiceResult getListByStatus(HoaDonChiTietRequest request, UserAuthentication ua) {
        List<HoaDon> hoaDons = service.findByIdNguoiDungAndTrangThai(ua.getPrincipal(), request.getStatus());

        List<HoaDonModel> models = hoaDons.stream().map(hoaDon -> {
            HoaDonModel model = hoaDonTransformer.toModel(hoaDon);

            List<HoaDonChiTietModel> hoaDonChiTietModels = hoaDonChiTietService.findByIdHoaDon(hoaDon.getId()).stream()
                    .map(hoaDonChiTiet -> {
                        HoaDonChiTietModel hoaDonChiTietModel = hoaDonChiTietTransformer.toModel(hoaDonChiTiet);

                        sanPhamChiTietService.findById(hoaDonChiTiet.getIdSanPhamChiTiet()).ifPresent(spct -> {
                            SanPhamChiTietModel spctModel = sanPhamChiTietTransformer.toModel(spct);
                            sanPhamService.findById(spct.getIdSanPham()).ifPresent(sanPham ->
                                    spctModel.setSanPhamModel(sanPhamTransformer.toModel(sanPham))
                            );
                            sizeService.findById(spct.getIdSize()).ifPresent(spctModel::setSize);
                            mauSacService.findById(spct.getIdMauSac()).ifPresent(spctModel::setMauSac);
                            hoaDonChiTietModel.setSanPhamChiTietModel(spctModel);
                        });

                        return hoaDonChiTietModel;
                    })
                    .collect(Collectors.toList());

            model.setHoaDonChiTietModels(hoaDonChiTietModels);
            return model;
        }).collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

    @Transactional(rollbackOn = Exception.class)
    public ServiceResult cancelOrder(Long id, CancelOrderRequest request, UserAuthentication ua) throws IOException, InterruptedException {
        HoaDon hoaDon = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tồn tại"));
        if (hoaDon.getTrangThai() != StatusHoaDon.CHO_XAC_NHAN.getValue() &&
                hoaDon.getTrangThai() != StatusHoaDon.CHO_THANH_TOAN.getValue()
                && hoaDon.getTrangThai() != StatusHoaDon.VAN_CHUYEN.getValue()
                && hoaDon.getTrangThai() != StatusHoaDon.CHO_GIAO_HANG.getValue()) {
            throw new IllegalArgumentException("Hóa đơn không thể hủy vì trạng thái không hợp lệ.");
        }
        if (hoaDon.getNgayThanhToan() == null) {
            cancelOrderDetails(hoaDon);
            hoaDon.setTrangThai(StatusHoaDon.DA_HUY.getValue());
        } else {
            String ipAddress = VNPayUtil.getIpAddress(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            Long amount = hoaDon.getTongTien().longValue() * 100L;
            ServiceResult result = paymentService.refundTransaction(hoaDon.getMa(), amount, ipAddress, ua);

            if (!SystemConstant.STATUS_SUCCESS.equals(result.getMessage())) {
                return new ServiceResult("Hoàn tiền thất bại", SystemConstant.STATUS_FAIL, SystemConstant.CODE_400);
            }

            cancelOrderDetails(hoaDon);
            hoaDon.setTrangThai(StatusHoaDon.DA_HUY.getValue());
        }

        hoaDon.setLyDoHuy(request.getOrderInfo());
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setNguoiCapNhat(ua.getPrincipal());
        service.save(hoaDon);

        return new ServiceResult();
    }

    private void cancelOrderDetails(HoaDon hoaDon) {
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietService.findByIdHoaDon(hoaDon.getId());
        hoaDonChiTiets.forEach(hoaDonChiTiet -> {
            hoaDonChiTiet.setTrangThai(StatusHoaDon.DA_HUY.getValue());
            Optional<SanPhamChiTiet> sanPhamChiTietOpt = sanPhamChiTietService.findById(hoaDonChiTiet.getIdSanPhamChiTiet());
            sanPhamChiTietOpt.ifPresent(sanPhamChiTiet -> {
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + hoaDonChiTiet.getSoLuong());
                sanPhamChiTietService.save(sanPhamChiTiet);
            });
        });
        hoaDonChiTietService.saveAll(hoaDonChiTiets);
    }

    public ServiceResult get(Long id) {
        HoaDon hoaDon = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tồn tại"));

        HoaDonModel model = hoaDonTransformer.toModel(hoaDon);

        // Lấy danh sách chi tiết hóa đơn
        List<HoaDonChiTietModel> hoaDonChiTietModels = hoaDonChiTietService.findByIdHoaDon(hoaDon.getId()).stream()
                .map(hoaDonChiTiet -> {
                    HoaDonChiTietModel hoaDonChiTietModel = hoaDonChiTietTransformer.toModel(hoaDonChiTiet);

                    sanPhamChiTietService.findById(hoaDonChiTiet.getIdSanPhamChiTiet()).ifPresent(spct -> {
                        SanPhamChiTietModel spctModel = sanPhamChiTietTransformer.toModel(spct);
                        sanPhamService.findById(spct.getIdSanPham()).ifPresent(sanPham ->
                                spctModel.setSanPhamModel(sanPhamTransformer.toModel(sanPham))
                        );
                        sizeService.findById(spct.getIdSize()).ifPresent(spctModel::setSize);
                        mauSacService.findById(spct.getIdMauSac()).ifPresent(spctModel::setMauSac);
                        hoaDonChiTietModel.setSanPhamChiTietModel(spctModel);
                    });

                    return hoaDonChiTietModel;
                })
                .collect(Collectors.toList());

        model.setHoaDonChiTietModels(hoaDonChiTietModels);

        return new ServiceResult(model, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }
    public ServiceResult getHoaDonDoiTra(UserAuthentication ua) {
        List<HoaDon> hoaDons = service.getHoaDonsByIdNguoiDungAndTrangThaiDoiTraNotNull(ua.getPrincipal());

        List<HoaDonModel> models = hoaDons.stream().map(hoaDon -> {
            HoaDonModel model = hoaDonTransformer.toModel(hoaDon);

            List<HoaDonChiTietModel> hoaDonChiTietModels = hoaDonChiTietService.findByIdHoaDon(hoaDon.getId()).stream()
                    .map(hoaDonChiTiet -> {
                        HoaDonChiTietModel hoaDonChiTietModel = hoaDonChiTietTransformer.toModel(hoaDonChiTiet);

                        sanPhamChiTietService.findById(hoaDonChiTiet.getIdSanPhamChiTiet()).ifPresent(spct -> {
                            SanPhamChiTietModel spctModel = sanPhamChiTietTransformer.toModel(spct);
                            sanPhamService.findById(spct.getIdSanPham()).ifPresent(sanPham ->
                                    spctModel.setSanPhamModel(sanPhamTransformer.toModel(sanPham))
                            );
                            sizeService.findById(spct.getIdSize()).ifPresent(spctModel::setSize);
                            mauSacService.findById(spct.getIdMauSac()).ifPresent(spctModel::setMauSac);
                            hoaDonChiTietModel.setSanPhamChiTietModel(spctModel);
                        });

                        return hoaDonChiTietModel;
                    })
                    .collect(Collectors.toList());

            model.setHoaDonChiTietModels(hoaDonChiTietModels);
            return model;
        }).collect(Collectors.toList());

        return new ServiceResult(models, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
    }

}

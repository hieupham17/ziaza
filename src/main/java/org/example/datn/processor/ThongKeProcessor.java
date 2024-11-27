//package org.example.datn.processor;
//
//import org.example.datn.constants.SystemConstant;
//import org.example.datn.entity.GioHangChiTiet;
//import org.example.datn.entity.HoaDon;
//import org.example.datn.entity.HoaDonChiTiet;
//import org.example.datn.entity.ThanhToan;
//import org.example.datn.model.ServiceResult;
//import org.example.datn.model.UserAuthentication;
//import org.example.datn.model.enums.StatusGioHang;
//import org.example.datn.model.enums.StatusHoaDon;
//import org.example.datn.model.enums.StatusThanhToan;
//import org.example.datn.model.enums.TypeThanhToan;
//import org.example.datn.model.request.HoaDonRequest;
//import org.example.datn.model.response.HoaDonChiTietModel;
//import org.example.datn.model.response.HoaDonModel;
//import org.example.datn.repository.HoaDonChiTietRepository;
//import org.example.datn.repository.HoaDonRepository;
//import org.example.datn.service.*;
//import org.example.datn.transformer.HoaDonChiTietTransformer;
//import org.example.datn.transformer.HoaDonTransformer;
//import org.example.datn.transformer.SanPhamChiTietTransformer;
//import org.example.datn.transformer.SanPhamTransformer;
//import org.example.datn.utils.VNPayUtil;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.persistence.EntityNotFoundException;
//import javax.servlet.http.HttpServletRequest;
//import javax.transaction.Transactional;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.stream.Collectors;
//
///**
// * @author hoangKhong
// */
//@Component
//public class ThongKeProcessor {
//    @Autowired
//    HoaDonService service;
//
//    @Autowired
//    HoaDonTransformer hoaDonTransformer;
//
//    @Autowired
//    DiaChiGiaoHangProcessor diaChiGiaoHangProcessor;
//
//    @Autowired
//    PhuongThucVanChuyenProcessor phuongThucVanChuyenProcessor;
//
//    @Autowired
//    UserProcessor userProcessor;
//
//    @Autowired
//    GioHangChiTietService gioHangChiTietService;
//
//    @Autowired
//    GioHangService gioHangService;
//
//    @Autowired
//    HoaDonChiTietService hoaDonChiTietService;
//
//    @Autowired
//    PhuongThucThanhToanService phuongThucThanhToanService;
//
//    @Autowired
//    ThanhToanService thanhToanService;
//
//    @Autowired
//    HoaDonService hoaDonService;
//    @Autowired
//    HoaDonChiTietTransformer hoaDonChiTietTransformer;
//    @Autowired
//    SanPhamChiTietService sanPhamChiTietService;
//    @Autowired
//    SanPhamChiTietTransformer sanPhamChiTietTransformer;
//    @Autowired
//    private SanPhamService sanPhamService;
//    @Autowired
//    private SanPhamTransformer sanPhamTransformer;
//    @Autowired
//    private SizeService sizeService;
//    @Autowired
//    private MauSacService mauSacService;
//
//    @Autowired
//    SanPhamChiTietProcessor sanPhamChiTietprocessor;
//
//    public ServiceResult getAll() {
//        var list = service.getAll();
//
//        var hoadon = list.stream().map(hoaDon -> {
//            var model = toModel(hoaDon);
//            var diaChiGiaoHang = diaChiGiaoHangProcessor.findById(hoaDon.getIdDiaChiGiaoHang());
//            var phuongThucVanChuyen = phuongThucVanChuyenProcessor.findById(hoaDon.getIdPhuongThucVanChuyen());
//            var user = userProcessor.findById(hoaDon.getIdNguoiDung());
//            model.setUserModel(user);
//            model.setDiaChiGiaoHangModel(diaChiGiaoHang);
//            model.setPhuongThucVanChuyenModel(phuongThucVanChuyen);
//            return model;
//        }).collect(Collectors.toList());
//
//        BigDecimal doanhThu = list.stream()
//                .map(HoaDon::getTongTien)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        long soDonHang = list.size();
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("doanhThu", doanhThu);
//        result.put("soDonHang", soDonHang);
//
//        return new ServiceResult(hoadon, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }
//
//    public ServiceResult getHSCT() {
//        var list2 = hoaDonChiTietService.findAll();
//        var hdct = list2.stream().map(hoaDonChiTiet -> {
//            var model = toModel(hoaDonChiTiet);
//            var sanPhamChiTiet = sanPhamChiTietprocessor.findById(hoaDonChiTiet.getIdSanPhamChiTiet());
//            model.setSanPhamChiTietModel(sanPhamChiTiet);
//            return model;
//        }).collect(Collectors.toList());
//        // Đơn hàng
//
//        // Sản phẩm bán được
//        long soSanPhamBanDuoc = list2.stream()
//                .flatMap(hoaDon -> hoaDonChiTietService.findByIdHoaDon(hoaDon.getId()).stream())
//                .mapToLong(HoaDonChiTiet::getSoLuong)
//                .sum();
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("soSanPhamBanDuoc", soSanPhamBanDuoc);
//        return new ServiceResult(hdct, SystemConstant.STATUS_SUCCESS, SystemConstant.CODE_200);
//    }
//
//    private HoaDonModel toModel(HoaDon hoaDon) {
//        if (hoaDon == null) {
//            return null;
//        }
//
//        HoaDonModel model = new HoaDonModel();
//        model.setId(hoaDon.getId());
//        model.setIdNguoiDung(hoaDon.getIdNguoiDung());
//        model.setIdDiaChiGiaoHang(hoaDon.getIdDiaChiGiaoHang());
//        model.setIdPhuongThucVanChuyen(hoaDon.getIdPhuongThucVanChuyen());
//        model.setMa(hoaDon.getMa());
//        model.setNgayDatHang(hoaDon.getNgayDatHang());
//        model.setNgayThanhToan(hoaDon.getNgayThanhToan());
//        model.setTongTien(hoaDon.getTongTien());
//        model.setDiemSuDung(hoaDon.getDiemSuDung());
//        model.setTrangThai(hoaDon.getTrangThai());
//
//        return model;
//    }
//
//
//    private HoaDonChiTietModel toModel(HoaDonChiTiet hoaDon) {
//        if (hoaDon == null) {
//            return null;
//        }
//
//        HoaDonChiTietModel model = new HoaDonChiTietModel();
//        model.setId(hoaDon.getId());
//        model.setIdHoaDon(hoaDon.getIdHoaDon());
//        model.setGia(hoaDon.getGia());
//        model.setSoLuong(hoaDon.getSoLuong());
//        model.setIdSanPhamChiTiet(hoaDon.getIdSanPhamChiTiet());
//        model.setTrangThai(hoaDon.getTrangThai());
//
//        return model;
//    }
//
//
//}

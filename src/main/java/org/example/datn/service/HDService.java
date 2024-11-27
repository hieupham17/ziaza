package org.example.datn.service;

import org.example.datn.entity.DiemTichLuy;
import org.example.datn.entity.HoaDon;
import org.example.datn.entity.HoaDonChiTiet;
import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.model.request.HDRequest;
import org.example.datn.repository.DiemTichLuyRepository;
import org.example.datn.repository.HoaDonChiTietRepository;
import org.example.datn.repository.HoaDonRepository;
import org.example.datn.repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HDService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private DiemTichLuyRepository diemTichLuyRepository;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

//    @Transactional
//    public HoaDon taoHoaDon(HDRequest hdRequest) {
//
//        // Tạo hóa đơn
//        HoaDon hoaDon = new HoaDon();
//        hoaDon.setIdNguoiDung(hdRequest.getIdCustomer());
//        hoaDon.setTongTien(hdRequest.getTotalBill());
//        hoaDon.setDiemSuDung(hdRequest.getDiemSuDung()); // Lưu số điểm đã sử dụng
//        hoaDon.setNgayTao(LocalDateTime.now());
//        hoaDon.setTrangThai(hdRequest.getTrangThai());
//        hoaDon.setNgayThanhToan(LocalDateTime.now());
//        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);
//
//        // Tạo mã hóa đơn
//        String maHoaDon = "HD" + String.format("%07d", savedHoaDon.getId());
//        savedHoaDon.setMa(maHoaDon);
//
//        // Lưu chi tiết hóa đơn
//        List<HoaDonChiTiet> chiTietList = hdRequest.getItems().stream().map(hdctRequest -> {
//            HoaDonChiTiet chiTiet = new HoaDonChiTiet();
//            chiTiet.setIdHoaDon(savedHoaDon.getId());
//            chiTiet.setIdSanPhamChiTiet(hdctRequest.getId());
//            chiTiet.setSoLuong(hdctRequest.getSoLuong());
//            chiTiet.setNgayTao(LocalDateTime.now());
//            return chiTiet;
//        }).collect(Collectors.toList());
//        hoaDonChiTietRepository.saveAll(chiTietList);
//
//
//        return savedHoaDon;
//    }


    @Transactional
    public HoaDon taoHoaDon(HDRequest hdRequest) {

        // Tạo hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setIdNguoiDung(hdRequest.getIdCustomer());
        hoaDon.setTongTien(hdRequest.getTotalBill());
        hoaDon.setDiemSuDung(hdRequest.getDiemSuDung()); // Lưu số điểm đã sử dụng
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTrangThai(hdRequest.getTrangThai());
        hoaDon.setNgayThanhToan(LocalDateTime.now());
        hoaDon.setNgayDatHang(LocalDateTime.now());
        hoaDon.setNgayCapNhat(LocalDateTime.now());
        hoaDon.setIdDiaChiGiaoHang(hdRequest.getIdDiaChiGiaoHang());
        hoaDon.setIdPhuongThucVanChuyen(hdRequest.getIdPhuongThucVanChuyen());
        hoaDon.setNguoiCapNhat(hdRequest.getNguoiCapNhat());
        hoaDon.setNguoiTao(hdRequest.getNguoiTao());
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        // Tạo mã hóa đơn
        String maHoaDon = "HD" + String.format("%07d", savedHoaDon.getId());
        savedHoaDon.setMa(maHoaDon);

        // Lưu chi tiết hóa đơn và cập nhật số lượng sản phẩm
        List<HoaDonChiTiet> chiTietList = hdRequest.getItems().stream().map(hdctRequest -> {
            HoaDonChiTiet chiTiet = new HoaDonChiTiet();
            chiTiet.setIdHoaDon(savedHoaDon.getId());
            chiTiet.setIdSanPhamChiTiet(hdctRequest.getId());
            chiTiet.setSoLuong(hdctRequest.getSoLuong());
            chiTiet.setNgayTao(LocalDateTime.now());
            chiTiet.setNgayCapNhat(LocalDateTime.now());
            chiTiet.setTrangThai(hdctRequest.getTrangThai());

            // Giảm số lượng tồn kho của sản phẩm
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(hdctRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại với ID: " + hdctRequest.getId()));

            if (sanPhamChiTiet.getSoLuong() < hdctRequest.getSoLuong()) {
                throw new IllegalArgumentException("Sản phẩm không đủ số lượng trong kho: " + sanPhamChiTiet.getIdSanPham());
            }

            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - hdctRequest.getSoLuong());
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            return chiTiet;
        }).collect(Collectors.toList());

        hoaDonChiTietRepository.saveAll(chiTietList);

        return savedHoaDon;
    }


    @Transactional
    public void huyHoaDon(Long idHoaDon) {
        // Lấy hóa đơn từ DB
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với ID: " + idHoaDon));

        // Kiểm tra trạng thái hóa đơn (đã hủy thì không làm gì)
        if (hoaDon.getTrangThai() == 0) {
            throw new RuntimeException("Hóa đơn đã bị hủy trước đó.");
        }

        // Cập nhật trạng thái hóa đơn = 0
        hoaDon.setTrangThai(0);
        hoaDonRepository.save(hoaDon);

        // Kiểm tra và hoàn lại điểm tích lũy nếu đã sử dụng
        if (hoaDon.getDiemSuDung() > 0) {
            DiemTichLuy diemTichLuy = new DiemTichLuy();
            diemTichLuy.setIdNguoiDung(hoaDon.getIdNguoiDung());
            diemTichLuy.setDiem(hoaDon.getDiemSuDung()); // Trả lại điểm
            diemTichLuy.setLyDo("Hoàn điểm do hủy hóa đơn " + hoaDon.getMa());
            diemTichLuy.setNgayTao(LocalDateTime.now());
            diemTichLuyRepository.save(diemTichLuy);
        }
    }
}

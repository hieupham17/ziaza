package org.example.datn.controller;

import lombok.RequiredArgsConstructor;
import org.example.datn.entity.SanPhamChiTiet;
import org.example.datn.model.request.SanPhamChiTietRequest;
import org.example.datn.repository.SanPhamChiTietRepository;
import org.example.datn.service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

public class ProductDetailController {
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @GetMapping("/{idSanPham}")
    public SanPhamChiTietRequest getDetail(@PathVariable Long id) {
        Optional<SanPhamChiTiet> sanPhamChiTietRepositoryOptional = sanPhamChiTietRepository.findById(id);
        if (sanPhamChiTietRepositoryOptional.isPresent()) {
            SanPhamChiTiet spct = sanPhamChiTietRepositoryOptional.get();
            SanPhamChiTietRequest sanPhamChiTietRequest = new SanPhamChiTietRequest();
            spct.setId(spct.getId());
            sanPhamChiTietRequest.setTenSanPham(sanPhamChiTietRequest.getTenSanPham());
            sanPhamChiTietRequest.setTenMauSac(sanPhamChiTietRequest.getTenMauSac());
            sanPhamChiTietRequest.setTenChatLieu(sanPhamChiTietRequest.getTenChatLieu());
            sanPhamChiTietRequest.setTenKichThuoc(sanPhamChiTietRequest.getTenKichThuoc());
            sanPhamChiTietRequest.setSoLuong(sanPhamChiTietRequest.getSoLuong());
            sanPhamChiTietRequest.setGia(sanPhamChiTietRequest.getGia());
            return sanPhamChiTietRequest;
        }else {
            return null;
        }
    }
}

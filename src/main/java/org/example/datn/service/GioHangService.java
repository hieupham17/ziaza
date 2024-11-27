package org.example.datn.service;

import org.example.datn.entity.GioHang;
import org.example.datn.repository.GioHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GioHangService {
    @Autowired
    private GioHangRepository repo;

    public void save(GioHang gioHang) {
        repo.save(gioHang);
    }

    public Optional<GioHang> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<GioHang> findByIdNguoiDung(Long idNguoiDung) {
        return repo.findByIdNguoiDung(idNguoiDung);
    }


}

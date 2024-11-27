package org.example.datn.service;

import org.example.datn.entity.GioHangChiTiet;
import org.example.datn.model.ServiceResult;
import org.example.datn.repository.GioHangChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository repo;

    public void save(GioHangChiTiet gioHangChiTiet) {
        repo.save(gioHangChiTiet);
    }

    public Optional<GioHangChiTiet> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<GioHangChiTiet> findByIdGioHang(Long idGioHang){
        return repo.findByIdGioHang(idGioHang);
    }

    public List<GioHangChiTiet> findByIdGioHangAndTrangThai(Long idGioHang, Integer status){
        return repo.findByIdGioHangAndTrangThai(idGioHang, status);
    }

    public Optional<GioHangChiTiet> findByIdGioHangAndIdSanPhamChiTietAndTrangThai(Long idGioHang, Long idSanPhamChiTiet, Integer trangThai){
        return repo.findByIdGioHangAndIdSanPhamChiTietAndTrangThai(idGioHang, idSanPhamChiTiet, trangThai);
    }

    public List<GioHangChiTiet> findByIdIn(List<Long> ids){
        return repo.findByIdIn(ids);
    }

}

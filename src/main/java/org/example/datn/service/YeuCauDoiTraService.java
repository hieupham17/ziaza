package org.example.datn.service;

import org.example.datn.entity.SanPham;
import org.example.datn.entity.YeuCauDoiTra;
import org.example.datn.model.enums.LoaiYeuCau;
import org.example.datn.repository.SanPhamRepository;
import org.example.datn.repository.YeuCauDoiTraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YeuCauDoiTraService {
    @Autowired
    private YeuCauDoiTraRepository repo;

    public Optional<YeuCauDoiTra> findById(Long id) {
        return repo.findById(id);
    }

    public List<YeuCauDoiTra> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    public List<YeuCauDoiTra> findAll() {
        return repo.findAll();
    }

    public void save(YeuCauDoiTra yeuCauDoiTra) {
        repo.save(yeuCauDoiTra);
    }

    public void update(YeuCauDoiTra sanPham) {
        repo.save(sanPham); // save() sẽ tự động cập nhật nếu đã có id
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<YeuCauDoiTra> findByLoaiAndTrangThai(LoaiYeuCau loai, Integer trangThai) {
        return repo.findByLoaiAndTrangThai(loai, trangThai);
    }
    public List<YeuCauDoiTra> findByLoai(LoaiYeuCau loai) {
        return repo.findByLoai(loai);
    }
}

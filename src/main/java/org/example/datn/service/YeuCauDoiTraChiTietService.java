package org.example.datn.service;

import org.example.datn.entity.HoaDonChiTiet;
import org.example.datn.entity.YeuCauDoiTra;
import org.example.datn.entity.YeuCauDoiTraChiTiet;
import org.example.datn.repository.YeuCauDoiTraChiTietRepository;
import org.example.datn.repository.YeuCauDoiTraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YeuCauDoiTraChiTietService {
    @Autowired
    private YeuCauDoiTraChiTietRepository repo;

    public Optional<YeuCauDoiTraChiTiet> findById(Long id) {
        return repo.findById(id);
    }

    public List<YeuCauDoiTraChiTiet> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    public List<YeuCauDoiTraChiTiet> findAll() {
        return repo.findAll();
    }

    public void save(YeuCauDoiTraChiTiet sanPham) {
        repo.save(sanPham);
    }

    public void update(YeuCauDoiTraChiTiet sanPham) {
        repo.save(sanPham); // save() sẽ tự động cập nhật nếu đã có id
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
    public List<YeuCauDoiTraChiTiet> findByIdYeuCauDoiTra(Long idDoiTra) {
        return repo.findByIdYeuCauDoiTra(idDoiTra);
    }
    public void saveAll(List<YeuCauDoiTraChiTiet> yeuCauDoiTraChiTiets) {
        repo.saveAll(yeuCauDoiTraChiTiets);
    }
}

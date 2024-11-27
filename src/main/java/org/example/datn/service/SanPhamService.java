package org.example.datn.service;

import org.example.datn.entity.SanPham;
import org.example.datn.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {
    @Autowired
    private SanPhamRepository repo;

    public Optional<SanPham> findById(Long id) {
        return repo.findById(id);
    }

    public List<SanPham> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    public List<SanPham> findAll() {
        return repo.findAll();
    }

    public void save(SanPham sanPham) {
        repo.save(sanPham);
    }

    public void update(SanPham sanPham) {
        repo.save(sanPham); // save() sẽ tự động cập nhật nếu đã có id
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<SanPham> searchProductsByName(String ten) {
        return repo.findByTenContaining(ten);
    }

}

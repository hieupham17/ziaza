package org.example.datn.service;

import org.example.datn.entity.BlogChiTiet;
import org.example.datn.entity.SanPham;
import org.example.datn.repository.BlogChiTietRepository;
import org.example.datn.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogChiTietService {
    @Autowired
    private BlogChiTietRepository repo;

    public Optional<BlogChiTiet> findById(Long id) {
        return repo.findById(id);
    }

    public List<BlogChiTiet> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    public List<BlogChiTiet> findAll() {
        return repo.findAll();
    }

    public void save(BlogChiTiet sanPham) {
        repo.save(sanPham);
    }

    public void update(BlogChiTiet sanPham) {
        repo.save(sanPham); // save() sẽ tự động cập nhật nếu đã có id
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}

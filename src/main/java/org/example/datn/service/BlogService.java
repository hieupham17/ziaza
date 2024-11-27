package org.example.datn.service;

import org.example.datn.entity.Blog;
import org.example.datn.entity.SanPham;
import org.example.datn.repository.BlogRepository;
import org.example.datn.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    private BlogRepository repo;

    public Optional<Blog> findById(Long id) {
        return repo.findById(id);
    }

    public List<Blog> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    public List<Blog> findAll() {
        return repo.findAll();
    }

    public void save(Blog sanPham) {
        repo.save(sanPham);
    }

    public void update(Blog sanPham) {
        repo.save(sanPham); // save() sẽ tự động cập nhật nếu đã có id
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}

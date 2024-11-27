package org.example.datn.service;

import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.KhuyenMai;
import org.example.datn.entity.Size;
import org.example.datn.repository.ChatLieuRepository;
import org.example.datn.repository.KhuyenMaiRepository;
import org.example.datn.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhuyenMaiService {

    @Autowired
    private KhuyenMaiRepository repo;

    public Optional<KhuyenMai> findById(Long id) {
        return repo.findById(id);
    }

    public List<KhuyenMai> findAll() {
        return repo.findAll();
    }

    public void save(KhuyenMai chatLieu) {
        repo.save(chatLieu);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public void delete(KhuyenMai khuyenMai) {
        repo.delete(khuyenMai);
    }

    public KhuyenMai saveKm(KhuyenMai khuyenMai) {
        return repo.save(khuyenMai);
    }

    public boolean existsByMaAndIdNot(String ma,Long id) {
        return repo.existsByMaAndIdNot(ma, id);
    }

    public boolean existsByMa(String ma) {
        return repo.existsByMa(ma);
    }

    public List<KhuyenMai> findByKeywordAndLoai(String keyword, Integer loai) {
        return repo.findByKeywordAndLoai(keyword, loai);
    }

    public List<KhuyenMai> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }
}

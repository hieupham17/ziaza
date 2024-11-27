package org.example.datn.service;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.PhuongThucThanhToan;
import org.example.datn.repository.PhuongThucThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhuongThucThanhToanService {
    @Autowired
    PhuongThucThanhToanRepository repo;

    public Optional<PhuongThucThanhToan> findById(Long id) {
        return repo.findById(id);
    }

    public List<PhuongThucThanhToan> findAll() {
        return repo.findAll();
    }

    public Optional<PhuongThucThanhToan> getActive() {
        return repo.findByTrangThai(SystemConstant.ACTIVE);
    }
}

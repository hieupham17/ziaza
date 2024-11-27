package org.example.datn.service;

import org.example.datn.entity.ThanhToan;
import org.example.datn.repository.ThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThanhToanService {

    @Autowired
    ThanhToanRepository repository;

    public void save(ThanhToan thanhToan){
        repository.save(thanhToan);
    }
    public Optional<ThanhToan> findByIdHoaDon(Long idHoaDon){
        return repository.findByIdHoaDon(idHoaDon);
    }

}

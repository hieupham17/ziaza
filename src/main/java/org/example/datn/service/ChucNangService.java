package org.example.datn.service;

import javax.persistence.EntityManager;

import org.example.datn.entity.ChucNang;
import org.example.datn.repository.ChucNangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hoangKhong
 */
@Service
public class ChucNangService {

    @Autowired
    private ChucNangRepository repo;
    private EntityManager entityManager;

    public ChucNangService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<String> findChucNangByNhomIds(List<Long> nhomIds) {
        return repo.findChucNangByNhomIds(nhomIds);
    }

    public List<ChucNang> findAllByIdNhom(Long idNhom) {
        var list = repo.findByIdNhom(idNhom);
        return list;
    }

    public List<ChucNang> findAllByOrderByNgayTaoDesc(){
        return repo.findAllByOrderByNgayTaoDesc();
    }
}

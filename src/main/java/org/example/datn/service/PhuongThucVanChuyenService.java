package org.example.datn.service;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.PhuongThucVanChuyen;
import org.example.datn.repository.PhuongThucVanChuyenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhuongThucVanChuyenService {

    @Autowired
    private PhuongThucVanChuyenRepository repo;

    public void save(PhuongThucVanChuyen entity) {
        repo.save(entity);
    }

    public void delete(PhuongThucVanChuyen entity) {
        repo.delete(entity);
    }

    public Optional<PhuongThucVanChuyen> findById(Long id) {
        return repo.findById(id);
    }

    public List<PhuongThucVanChuyen> getActive(){
        return repo.findByTrangThai(SystemConstant.ACTIVE);
    }
//    List<PhuongThucVanChuyen> findAll();
//    PhuongThucVanChuyen findById(Long id);
//
//    List<PhuongThucVanChuyen> findByCateId(Long cid);
//
//    PhuongThucVanChuyen create(PhuongThucVanChuyen product);
//
//    PhuongThucVanChuyen update(PhuongThucVanChuyen product);
//
//    void delete(Long id);
}

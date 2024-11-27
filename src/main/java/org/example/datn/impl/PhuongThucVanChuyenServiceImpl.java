package org.example.datn.impl;

import org.example.datn.entity.HoaDonChiTiet;
import org.example.datn.entity.PhuongThucVanChuyen;
import org.example.datn.repository.HoaDonChiTietRepository;
import org.example.datn.repository.PhuongThucVanChuyenRepository;
import org.example.datn.service.HoaDonChiTietService;
import org.example.datn.service.PhuongThucVanChuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhuongThucVanChuyenServiceImpl{

    @Autowired
    PhuongThucVanChuyenRepository phuongThucVanChuyenRepository;
//
//    @Override
//    public List<PhuongThucVanChuyen> findAll() {
//        return phuongThucVanChuyenRepository.findAll();
//    }
//
//    @Override
//    public PhuongThucVanChuyen findById(Long id) {
//        return phuongThucVanChuyenRepository.findById(id).get();
//    }
//
//    @Override
//    public List<PhuongThucVanChuyen> findByCateId(Long cid) {
//        return phuongThucVanChuyenRepository.findByCateId(cid);
//    }
//    @Override
//    public PhuongThucVanChuyen create(PhuongThucVanChuyen product) {
//        return phuongThucVanChuyenRepository.save(product);
//    }
//
//    @Override
//    public PhuongThucVanChuyen update(PhuongThucVanChuyen product) {
//        return phuongThucVanChuyenRepository.save(product);
//    }
//
//    @Override
//    public void delete(Long id) {
//        phuongThucVanChuyenRepository.deleteById(id);
//    }

}

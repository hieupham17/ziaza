package org.example.datn.impl;

import org.example.datn.entity.HoaDon;
import org.example.datn.entity.HoaDonChiTiet;
import org.example.datn.repository.HoaDonChiTietRepository;
import org.example.datn.repository.HoaDonRepository;
import org.example.datn.service.HoaDonChiTietService;
import org.example.datn.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonChiTietServiceImpl {

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

//    @Override
//    public List<HoaDonChiTiet> findAll() {
//        return hoaDonChiTietRepository.findAll();
//    }
//
//    @Override
//    public HoaDonChiTiet findById(Long id) {
//        return hoaDonChiTietRepository.findById(id).get();
//    }
//
//    @Override
//    public List<HoaDonChiTiet> findByCateId(Long cid) {
//        return hoaDonChiTietRepository.findByCateId(cid);
//    }
//
//    @Override
//    public HoaDonChiTiet create(HoaDonChiTiet product) {
//        return hoaDonChiTietRepository.save(product);
//    }
//
//    @Override
//    public HoaDonChiTiet update(HoaDonChiTiet product) {
//        return hoaDonChiTietRepository.save(product);
//    }
//
//    @Override
//    public void delete(Long id) {
//        hoaDonChiTietRepository.deleteById(id);
//    }

}

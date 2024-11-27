package org.example.datn.impl;

import org.example.datn.entity.DiaChiGiaoHang;
import org.example.datn.repository.DiaChiGiaoHangRepository;
import org.example.datn.service.DiaChiGiaoHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaChiGiaoHangServiceImpl {

    @Autowired
    DiaChiGiaoHangRepository diaChiGiaHangRepository;
//
//    @Override
//    public List<DiaChiGiaoHang> findAll() {
//        return diaChiGiaHangRepository.findAll();
//    }
//
//    @Override
//    public DiaChiGiaoHang findById(Long id) {
//        return diaChiGiaHangRepository.findById(id).get();
//    }
//
//    @Override
//    public List<DiaChiGiaoHang> findByCateId(Long cid) {
//        return diaChiGiaHangRepository.findByCateId(cid);
//    }
//    @Override
//    public DiaChiGiaoHang create(DiaChiGiaoHang product) {
//        return diaChiGiaHangRepository.save(product);
//    }
//
//    @Override
//    public DiaChiGiaoHang update(DiaChiGiaoHang product) {
//        return diaChiGiaHangRepository.save(product);
//    }
//
//    @Override
//    public void delete(Long id) {
//        diaChiGiaHangRepository.deleteById(id);
//    }

}

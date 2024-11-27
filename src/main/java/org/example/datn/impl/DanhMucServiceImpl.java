//package org.example.datn.impl;
//
//import org.example.datn.entity.DanhMuc;
//import org.example.datn.entity.Thuonghieu;
//import org.example.datn.repository.DanhMucRepository;
//import org.example.datn.repository.ThuongHieuRepository;
//import org.example.datn.service.DanhMucService;
//import org.example.datn.service.ThuongHieuService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DanhMucServiceImpl implements DanhMucService {
//
//    @Autowired
//    DanhMucRepository danhMucRepository;
//
//    @Override
//    public List<DanhMuc> findAll() {
//        return danhMucRepository.findAll();
//    }
//
//    @Override
//    public DanhMuc findById(Long id) {
//        return danhMucRepository.findById(id).get();
//    }
//
//    @Override
//    public List<DanhMuc> findByCateId(Long cid) {
//        return danhMucRepository.findByCateId(cid);
//    }
//
//    @Override
//    public DanhMuc create(DanhMuc product) {
//        return danhMucRepository.save(product);
//    }
//
//    @Override
//    public DanhMuc update(DanhMuc product) {
//        return danhMucRepository.save(product);
//    }
//
//    @Override
//    public void delete(Long id) {
//        danhMucRepository.deleteById(id);
//    }
//
//}

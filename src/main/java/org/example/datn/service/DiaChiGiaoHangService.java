package org.example.datn.service;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.DiaChiGiaoHang;
import org.example.datn.repository.DiaChiGiaoHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaChiGiaoHangService {
    @Autowired
    private DiaChiGiaoHangRepository repo;

    public List<DiaChiGiaoHang> getAll() {
        return repo.findAll();
    }

    public Optional<DiaChiGiaoHang> findById(Long id) {
        return repo.findById(id);
    }

    public void save(DiaChiGiaoHang diaChiGiaoHang) {
        repo.save(diaChiGiaoHang);
    }

    public void delete(DiaChiGiaoHang diaChiGiaoHang) {
        repo.delete(diaChiGiaoHang);
    }

    public List<DiaChiGiaoHang> findByIdNguoiDung(Long idNguoiDung) {
        return repo.findByIdNguoiDung(idNguoiDung);
    }

    public Optional<DiaChiGiaoHang> findByIdNguoiDungAndTrangThai(Long idNguoiDung, Integer trangThai) {
        return repo.findByIdNguoiDungAndTrangThai(idNguoiDung, trangThai);
    }

    public List<DiaChiGiaoHang> findByTrangThaiDefault(Integer trangThai){
        return repo.findByTrangThai(trangThai);
    }

}

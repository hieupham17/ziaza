package org.example.datn.service;

import feign.Param;
import org.example.datn.entity.ApDungKhuyenMai;
import org.example.datn.entity.ChatLieu;
import org.example.datn.repository.ApDungKhuyenMaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApDungKhuyenMaiService {
    @Autowired
    private ApDungKhuyenMaiRepository repo;

    public void save(ApDungKhuyenMai entity) {
        repo.save(entity);
    }

    public void deleteByKhuyenMaiAndNotIn(Long idKhuyenMai, List<Long> idList){
        repo.deleteByKhuyenMaiAndNotIn(idKhuyenMai,idList);
    }

    public List<ApDungKhuyenMai> findByIdKhuyenMai(Long idKhuyenMai){
        return repo.findByIdKhuyenMai(idKhuyenMai);
    }
    public void delete(ApDungKhuyenMai apDungKhuyenMai){
        repo.delete(apDungKhuyenMai);
    }

    public List<ApDungKhuyenMai> findByIdNguoiDungAndDaSuDung(Long idNguoiDung, Boolean daSuDung){
        return repo.findByIdNguoiDungAndDaSuDung(idNguoiDung, daSuDung);
    }
    public Optional<ApDungKhuyenMai> findByIdKhuyenMaiAndIdNguoiDung(Long idKhuyenMai, Long idNguoiDung){
        return repo.findByIdKhuyenMaiAndIdNguoiDung(idKhuyenMai, idNguoiDung);
    }

    public List<ApDungKhuyenMai> findAll() {
        return repo.findAll();
    }
}

package org.example.datn.service;

import org.example.datn.constants.SystemConstant;
import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.Thuonghieu;
import org.example.datn.repository.DanhMucRepository;
import org.example.datn.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThuongHieuService {

    @Autowired
    private ThuongHieuRepository repo;

    public List<Thuonghieu> getAll() {
        return repo.findAll();
    }

    public void save(Thuonghieu thuonghieu) {
        repo.save(thuonghieu);
    }

    public Optional<Thuonghieu> findById(Long id) {
        return repo.findById(id);
    }

    public void delete(Thuonghieu thuonghieu) {
        repo.delete(thuonghieu);
    }

//    public List<DanhMuc> getActive() {
//        return repo.findByTrangThai(SystemConstant.ACTIVE);
//    }
}

package org.example.datn.service;

import org.example.datn.entity.HinhAnh;
import org.example.datn.repository.HinhAnhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HinhAnhServices {

    @Autowired
    HinhAnhRepository hinhAnhRepository ;
    public List<HinhAnh> getImagesByProductId(Long productId) {
        return hinhAnhRepository.findByIdSanPham(productId);
    }

    public void save(HinhAnh hinhAnh) {
        hinhAnhRepository.save(hinhAnh);
    }

}

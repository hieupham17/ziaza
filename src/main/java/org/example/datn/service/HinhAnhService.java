package org.example.datn.service;

import org.example.datn.entity.HinhAnh;
import org.example.datn.entity.MauSac;
import org.example.datn.model.response.HinhAnhModel;
import org.example.datn.repository.HinhAnhRepository;
import org.example.datn.repository.MauSacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface HinhAnhService {

    List<HinhAnh> getAllProducts();

    Optional<HinhAnh> getProductById(Long productId);

    List<HinhAnh> createProduct(HinhAnhModel model, MultipartFile[] images);

    HinhAnh updateProduct(Long productId, HinhAnhModel model, MultipartFile[] images);

    void deleteProduct(Long productId);

    List<HinhAnh> searchProductByName(String productName);
    void updateTrangThai(Long id, Integer trangThai);


}

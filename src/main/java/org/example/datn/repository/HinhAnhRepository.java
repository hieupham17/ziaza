package org.example.datn.repository;

import org.example.datn.entity.HinhAnh;
import org.example.datn.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HinhAnhRepository extends JpaRepository<HinhAnh, Long> {
    @Query("SELECT p FROM HinhAnh p WHERE p.id = ?1")
    List<HinhAnh> findByCateId(Long cid);

    Optional<HinhAnh> findByAnh(String anh);

    // Truy vấn lấy tất cả hình ảnh của sản phẩm dựa trên id_san_pham
    List<HinhAnh> findByIdSanPham(Long idSanPham);
}

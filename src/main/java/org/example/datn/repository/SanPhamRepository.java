package org.example.datn.repository;

import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.HinhAnh;
import org.example.datn.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {

    List<SanPham> findByIdIn(List<Long> ids);

    @Query("SELECT sp FROM SanPham sp WHERE sp.ten LIKE %:ten%")
    List<SanPham> findByTenContaining(@Param("ten") String ten);

    // Lấy sản phẩm sắp xếp theo giá tăng dần
    List<SanPham> findAllByOrderByGiaAsc();

    // Lấy sản phẩm sắp xếp theo giá giảm dần
    List<SanPham> findAllByOrderByGiaDesc();

}

package org.example.datn.repository;

import org.example.datn.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> {
    List<GioHangChiTiet> findByIdGioHang(Long idGioHang);

    Optional<GioHangChiTiet> findByIdGioHangAndIdSanPhamChiTietAndTrangThai(Long idGioHang, Long idSanPhamChiTiet, Integer trangThai);

    List<GioHangChiTiet> findByIdIn(List<Long> ids);

    List<GioHangChiTiet> findByIdGioHangAndTrangThai(Long idGioHang, Integer status);
}

package org.example.datn.repository;

import org.example.datn.entity.DiaChiGiaoHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaChiGiaoHangRepository extends JpaRepository<DiaChiGiaoHang, Long> {
    @Query("SELECT p FROM DiaChiGiaoHang p WHERE p.id = ?1")
    List<DiaChiGiaoHang> findByCateId(Long cid);

    List<DiaChiGiaoHang> findByIdNguoiDung(Long idNguoiDung);

    Optional<DiaChiGiaoHang> findByIdNguoiDungAndTrangThai(Long idNguoiDung, Integer trangThai);

    List<DiaChiGiaoHang> findByTrangThai(Integer trangThai);
}

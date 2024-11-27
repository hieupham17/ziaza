package org.example.datn.repository;

import org.example.datn.entity.HoaDon;
import org.example.datn.entity.PhuongThucVanChuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhuongThucVanChuyenRepository extends JpaRepository<PhuongThucVanChuyen, Long> {
    @Query("SELECT p FROM PhuongThucVanChuyen p WHERE p.id = ?1")
    List<PhuongThucVanChuyen> findByCateId(Long cid);

    List<PhuongThucVanChuyen> findByTrangThai(Integer active);
}

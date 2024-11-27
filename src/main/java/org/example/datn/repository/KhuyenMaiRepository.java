package org.example.datn.repository;

import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {
    @Query("SELECT p FROM KhuyenMai p WHERE p.id = ?1")
    List<KhuyenMai> findByCateId(Long cid);

    boolean existsByMaAndIdNot(String ma, Long id);

    @Query("SELECT k FROM KhuyenMai k " +
            "WHERE (:keyword IS NULL OR k.ten LIKE %:keyword% OR k.ma LIKE %:keyword%) " +
            "AND (:loai IS NULL OR k.loai = :loai) " +
            "ORDER BY k.ngayTao DESC")
    List<KhuyenMai> findByKeywordAndLoai(String keyword, Integer loai);

    boolean existsByMa(String ma);

    List<KhuyenMai> findByIdIn(List<Long> ids);
}

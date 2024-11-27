package org.example.datn.repository;

import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.MauSac;
import org.example.datn.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {
    @Query("SELECT p FROM MauSac p WHERE p.id = ?1")
    List<MauSac> findByCateId(Long cid);

    List<MauSac> findByIdIn(List<Long> ids);
}

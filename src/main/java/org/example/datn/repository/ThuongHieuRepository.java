package org.example.datn.repository;

import org.example.datn.entity.Thuonghieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<Thuonghieu, Long> {
    @Query("SELECT p FROM Thuonghieu p WHERE p.id = ?1")
    List<Thuonghieu> findByCateId(Long cid);
}

package org.example.datn.repository;

import org.example.datn.entity.PhuongThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhuongThucThanhToanRepository extends JpaRepository<PhuongThucThanhToan, Long> {
    Optional<PhuongThucThanhToan> findByTrangThai(Integer trangThai);
}

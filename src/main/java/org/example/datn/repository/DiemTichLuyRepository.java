package org.example.datn.repository;

import org.example.datn.entity.DiemTichLuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiemTichLuyRepository extends JpaRepository<DiemTichLuy, Long> {
    Optional<DiemTichLuy> findByIdNguoiDung(Long idNguoiDung);

    // Truy vấn để tìm tổng điểm của người dùng theo trạng thái
    @Query("SELECT SUM(d.diem) FROM DiemTichLuy d WHERE d.idNguoiDung = :idNguoiDung")
    Integer findSumDiemByIdNguoiDung (@Param("idNguoiDung") Long idNguoiDung);

}

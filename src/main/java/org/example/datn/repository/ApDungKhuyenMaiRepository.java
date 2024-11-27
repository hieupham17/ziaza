package org.example.datn.repository;

import feign.Param;
import org.example.datn.entity.ApDungKhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApDungKhuyenMaiRepository extends JpaRepository<ApDungKhuyenMai, Long> {
    @Modifying
    @Query("DELETE FROM ApDungKhuyenMai a WHERE a.idKhuyenMai = :idKhuyenMai AND (a.idSanPham IS NOT NULL AND a.idSanPham NOT IN :idList OR a.idNguoiDung IS NOT NULL AND a.idNguoiDung NOT IN :idList)")
    void deleteByKhuyenMaiAndNotIn(@Param("idKhuyenMai") Long idKhuyenMai, @Param("idList") List<Long> idList);

    List<ApDungKhuyenMai> findByIdKhuyenMai(Long idKhuyenMai);

    List<ApDungKhuyenMai> findByIdNguoiDungAndDaSuDung(Long idNguoiDung, Boolean daSuDung);

    Optional<ApDungKhuyenMai> findByIdKhuyenMaiAndIdNguoiDung(Long idKhuyenMai, Long idNguoiDung);
}

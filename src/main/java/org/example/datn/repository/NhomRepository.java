package org.example.datn.repository;

import org.example.datn.entity.Nhom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hoangKhong
 */
@Repository
public interface NhomRepository extends JpaRepository<Nhom, Long> {

    @Query("SELECT n FROM Nhom n " +
            "JOIN NhomNguoiDung ng ON n.id = ng.idNhom " +
            "WHERE ng.userId = :userId")
    List<Nhom> findNhomByUserId(@Param("userId") Long userId);

    boolean existsAllByTen(String ten);

    boolean existsAllByTenAndIdNot(String ten, Long id);

    @Query("SELECT n FROM Nhom n " + "WHERE (:ten IS NULL OR LOWER(TRIM(n.ten)) LIKE LOWER(CONCAT('%', TRIM(:ten), '%')))") Page<Nhom> getList(@Param("ten") String ten, Pageable pageable);

}

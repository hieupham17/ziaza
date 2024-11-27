package org.example.datn.repository;

import org.example.datn.entity.ChucNang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hoangKhong
 */
@Repository
public interface ChucNangRepository extends JpaRepository<ChucNang, Long> {

    @Query("SELECT c.ma FROM ChucNang c WHERE c.id IN (SELECT n.idChucNang FROM NhomChucNang n WHERE n.idNhom IN :idNhom)")
    List<String> findChucNangByNhomIds(@Param("idNhom") List<Long> idNhom);

    @Query("SELECT c FROM ChucNang c JOIN NhomChucNang nc ON c.id = nc.idChucNang WHERE nc.idNhom = :idNhom")
    List<ChucNang> findByIdNhom(@Param("idNhom") Long idNhom);

    List<ChucNang> findAllByOrderByNgayTaoDesc();
}

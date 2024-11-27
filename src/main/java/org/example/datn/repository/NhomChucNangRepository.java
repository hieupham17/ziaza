package org.example.datn.repository;

import org.example.datn.entity.NhomChucNang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hoangKhong
 */
@Repository
public interface NhomChucNangRepository extends JpaRepository<NhomChucNang, Long> {
    @Transactional
    @Modifying
    void deleteAllByIdNhom(Long idNhom);
}

package org.example.datn.repository;

import org.example.datn.entity.NhomNguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhomNguoiDungRepository extends JpaRepository<NhomNguoiDung, Long> {
    List<NhomNguoiDung> findByIdNhom(Long idNhom);

    void deleteAllByIdNhom(Long idNhom);

}

package org.example.datn.repository;

import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.SanPhamDoiTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamDoiTraRepository extends JpaRepository<SanPhamDoiTra, Long> {

}

package org.example.datn.repository;

import org.example.datn.entity.YeuCauDoiTra;
import org.example.datn.entity.YeuCauDoiTraChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeuCauDoiTraChiTietRepository extends JpaRepository<YeuCauDoiTraChiTiet, Long> {
    @Query("SELECT p FROM YeuCauDoiTraChiTiet p WHERE p.id = ?1")
    List<YeuCauDoiTraChiTiet> findByCateId(Long cid);

    List<YeuCauDoiTraChiTiet> findByIdIn(List<Long> ids);

    List<YeuCauDoiTraChiTiet> findByIdYeuCauDoiTra(Long idYeuCauDoiTra);

}

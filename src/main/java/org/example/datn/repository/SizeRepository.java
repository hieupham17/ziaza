package org.example.datn.repository;

import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query("SELECT p FROM Size p WHERE p.id = ?1")
    List<Size> findByCateId(Long cid);

    List<Size> findByIdIn(List<Long> ids);
}

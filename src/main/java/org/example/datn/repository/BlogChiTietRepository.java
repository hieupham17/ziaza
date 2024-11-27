package org.example.datn.repository;

import org.example.datn.entity.Blog;
import org.example.datn.entity.BlogChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogChiTietRepository extends JpaRepository<BlogChiTiet, Long> {
    @Query("SELECT p FROM Blog p WHERE p.id = ?1")
    List<BlogChiTiet> findByCateId(Long cid);

    List<BlogChiTiet> findByIdIn(List<Long> ids);
}

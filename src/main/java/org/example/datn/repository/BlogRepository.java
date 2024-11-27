package org.example.datn.repository;

import org.example.datn.entity.Blog;
import org.example.datn.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query("SELECT p FROM Blog p WHERE p.id = ?1")
    List<Blog> findByCateId(Long cid);

    List<Blog> findByIdIn(List<Long> ids);
}

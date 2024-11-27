package org.example.datn.repository;

import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Long> {
    @Query("SELECT p FROM ChatLieu p WHERE p.id = ?1")
    List<ChatLieu> findByCateId(Long cid);
}

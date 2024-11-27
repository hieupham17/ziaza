package org.example.datn.repository;

import org.example.datn.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hoangKhong
 */
@Repository
public interface ThongTinCaNhanRepository extends JpaRepository<Profile, Long> {
}

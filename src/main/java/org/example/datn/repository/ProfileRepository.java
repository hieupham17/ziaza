package org.example.datn.repository;

import org.example.datn.entity.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hoangKhong
 */
@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {


    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Profile> findByEmail(String email);

    boolean existsByCccd(String cccd);

    boolean existsByEmailAndId(String email, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByCccdAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    Optional<Profile> findByUserId(Long userId);
}

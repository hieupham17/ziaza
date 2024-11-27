package org.example.datn.repository;

import feign.Param;
import org.example.datn.entity.User;
import org.example.datn.model.enums.UserRoles;
import org.example.datn.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author hoangKhong
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameAndStatus(String username, UserStatus userStatus);

    Optional<User> findByIdAndStatus(Long id, UserStatus userStatus);

    List<User> findByIdIn(List<Long> ids);

    List<User> findAllByStatus(UserStatus status);

    boolean existsByUserName(String username);

    List<User> findByRole(UserRoles role);

    @Query("SELECT u FROM User u JOIN Profile p ON u.id = p.userId " +
            "WHERE (p.hoVaTen LIKE %:hoVaTen% OR p.phone LIKE %:phone%) " +
            "AND (:role IS NULL OR u.role = :role)")
    Page<User> getList(@Param("hoVaTen") String hoVaTen,
                                         @Param("role") UserRoles role,
                                         @Param("phone") String phone,
                                         Pageable pageable);


}

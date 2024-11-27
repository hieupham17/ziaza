package org.example.datn.repository;

import org.example.datn.entity.OtpRetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRetryRepository extends JpaRepository<OtpRetry, String> {
}

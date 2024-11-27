package org.example.datn.service;

import org.example.datn.entity.OtpRetry;
import org.example.datn.repository.OtpRetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OtpRetryService {
    @Autowired
    private OtpRetryRepository repo;
    protected String notFoundMessage() {
        return "otp.not-found.entity";
    }

    public Optional<OtpRetry> get(String id) {
        return repo.findById(id);
    }
    public void save(OtpRetry otpRetry) {
        repo.save(otpRetry);
    }
}

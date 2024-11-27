package org.example.datn.service;

import org.example.datn.entity.Otp;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    @Autowired
    private OtpRepository repo;


    protected String notFoundMessage() {
        return "otp.not-found.entity";
    }

    public Otp findById(long id) throws NotFoundEntityException {
        return repo.findById(id).orElseThrow(NotFoundEntityException.ofSupplier(notFoundMessage()));
    }

    public Otp getByCodeOrElseThrow(String code) throws NotFoundEntityException {
        return repo.findByCode(code)
                .orElseThrow(NotFoundEntityException.ofSupplier(notFoundMessage()));
    }

    public void delete(Otp otp){
        repo.delete(otp);
    }

    public void save(Otp otp){
        repo.save(otp);
    }
}

package org.example.datn.service;

import org.example.datn.entity.Profile;
import org.example.datn.entity.User;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author hoangKhong
 */
@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    public boolean emailExists(String email) {
        return profileRepository.existsByEmail(email);
    }

    public boolean phoneExists(String phone) {
        return profileRepository.existsByPhone(phone);
    }

    public boolean existsByCccd(String cccd) {
        return profileRepository.existsByCccd(cccd);
    }

    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    public boolean existsByEmailAndId(String email, Long id){ return profileRepository.existsByEmailAndId(email, id); }

    public boolean existsByEmailAndIdNot(String email, Long id){ return profileRepository.existsByEmailAndIdNot(email, id); }

    public boolean existsByCccdAndIdNot(String email, Long id){ return profileRepository.existsByCccdAndIdNot(email, id); }

    public boolean existsByPhoneAndIdNot(String phone, Long id){ return profileRepository.existsByPhoneAndIdNot(phone, id); }

    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id);
    }

    public Optional<Profile> findByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    public Profile getByEmailOrElseThrow(String email) throws NotFoundEntityException {
        return profileRepository.findByEmail(email)
                .orElseThrow(NotFoundEntityException.ofSupplier("email.not-found"));
    }

}

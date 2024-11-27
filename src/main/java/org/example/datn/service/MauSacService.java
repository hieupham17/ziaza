package org.example.datn.service;

import org.example.datn.entity.MauSac;
import org.example.datn.entity.Size;
import org.example.datn.repository.MauSacRepository;
import org.example.datn.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MauSacService {

    @Autowired
    private MauSacRepository repo;

    public Optional<MauSac> findById(Long id) {
        return repo.findById(id);
    }

    public List<MauSac> findAll() {
        return repo.findAll();
    }

    public void save(MauSac mauSac) {
        repo.save(mauSac);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }


    public List<MauSac> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }
}

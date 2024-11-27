package org.example.datn.service;

import org.example.datn.entity.Size;
import org.example.datn.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService {

    @Autowired
    private SizeRepository repo;

    public Optional<Size> findById(Long id) {
        return repo.findById(id);
    }

    public List<Size> findAll() {
        return repo.findAll();
    }

    public void save(Size size) {
        repo.save(size);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<Size> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }
}

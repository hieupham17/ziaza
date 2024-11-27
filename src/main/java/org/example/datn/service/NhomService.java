package org.example.datn.service;

import javax.persistence.EntityManager;

import org.example.datn.entity.Nhom;
import org.example.datn.model.request.GroupQueryModel;
import org.example.datn.repository.NhomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hoangKhong
 */
@Service
public class NhomService {
    @Autowired
    NhomRepository repo;

    private EntityManager entityManager;


    public NhomService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Nhom> findNhomByUserId(Long userId) {
        return repo.findNhomByUserId(userId);
    }

    public Nhom save(Nhom nhom) {
        return repo.save(nhom);
    }

    public boolean existsAllByTen(String ten) {
        return repo.existsAllByTen(ten);
    }

    public boolean existsAllByTenAndIdNot(String ten, Long id) {
        return repo.existsAllByTenAndIdNot(ten, id);
    }

    public Page<Nhom> getList(String ten, Integer page, Integer size) {
        int pageIndex = (page > 0) ? page - 1 : 0;
        return repo.getList(ten, PageRequest.of(pageIndex, size));
    }

    public Optional<Nhom> findById(Long id) {
        return repo.findById(id);
    }


}

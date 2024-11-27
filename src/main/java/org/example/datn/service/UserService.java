package org.example.datn.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.example.datn.entity.User;
import org.example.datn.exception.NotFoundEntityException;
import org.example.datn.model.enums.UserRoles;
import org.example.datn.model.enums.UserStatus;
import org.example.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hoangKhong
 */
@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Autowired
    public UserService(UserRepository repo, EntityManager entityManager) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.entityManager = entityManager;
    }

    public String notFoundMessage() {
        return "not.found";
    }

    public void save(User user) {
        repo.save(user);
    }

    public User saveEntity(User user) {
        return repo.save(user);
    }

    public String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }

    public boolean exitsUserName(String username) {
        return repo.existsByUserName(username);
    }


    public boolean passwordMatched(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    public List<User> findAllByStatus(UserStatus status) {
        return repo.findAllByStatus(status);
    }

    public Optional<User> findByUserName(String userName) {
        return repo.findByUserName(userName);
    }

    public Optional<User> getActive(String username) {
        return repo.findByUserNameAndStatus(username, UserStatus.ACTIVE);
    }

    public User getActiveOrElseThrow(Long id) throws NotFoundEntityException {
        return getActive(id)
                .orElseThrow(NotFoundEntityException.ofSupplier(notFoundMessage()));
    }

    public Optional<User> getActive(Long id) {
        return repo.findByIdAndStatus(id, UserStatus.ACTIVE);
    }

    public void update(Long id, UserStatus status) throws EntityNotFoundException {
        var user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not.found"));
        user.setStatus(status);
        repo.save(user);
    }

    public void update(Long id, String password) throws EntityNotFoundException {
        var user = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not.found"));
        user.setPassword(encodePassword(password));
        repo.save(user);
    }

    public List<User> findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    public List<User> findByRole(UserRoles role) {
        return repo.findByRole(role);
    }

    public void delete(User user) {
        repo.delete(user);
    }

    public Page<User> getList(String hoVaTen, UserRoles role, String phone, Integer page, Integer size) {
        int pageIndex = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(Sort.Order.desc("ngayTao")));

        return repo.getList(hoVaTen, role, phone, pageable);
    }
}
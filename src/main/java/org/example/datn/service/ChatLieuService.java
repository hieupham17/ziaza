package org.example.datn.service;

import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.MauSac;
import org.example.datn.repository.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuService {

    @Autowired
    private ChatLieuRepository repo;

    public Optional<ChatLieu> findById(Long id) {
        return repo.findById(id);
    }

    public List<ChatLieu> findAll() {
        return repo.findAll();
    }

    public void save(ChatLieu chatLieu) {
        repo.save(chatLieu);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}

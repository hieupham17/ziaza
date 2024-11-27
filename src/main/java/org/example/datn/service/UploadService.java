package org.example.datn.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UploadService {
    void save(MultipartFile file);
}

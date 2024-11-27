package org.example.datn.controller;

import lombok.RequiredArgsConstructor;
import org.example.datn.service.UploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;
    @PostMapping("/upload")
    public void uploadImage(@RequestParam MultipartFile file) {
        uploadService.save(file);
    }


}

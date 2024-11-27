package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.MauSac;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.processor.ChatLieuProcessor;
import org.example.datn.processor.MauSacProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("ChatLieuApi")
@RequestMapping("/chat-lieu")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatLieuController {
    @Autowired
    ChatLieuProcessor processor;

    @GetMapping("/get-list")
    public ResponseEntity<ServiceResult> getList() {
        return ResponseEntity.ok(processor.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ServiceResult> create(@RequestBody @Valid ChatLieu request, UserAuthentication ua) {
        ServiceResult result = processor.save(request, ua);
        return new ResponseEntity<>(result, HttpStatus.CREATED); // HttpStatus.CREATED cho response 201
    }

    // Cập nhật danh mục theo ID với thông tin UserAuthentication
    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceResult> update(@PathVariable Long id, @RequestBody @Valid ChatLieu request, UserAuthentication ua) {
        ServiceResult result = processor.update(id, request, ua);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ServiceResult> delete(@PathVariable Long id) {
        return ResponseEntity.ok(processor.delete(id));
    }
}

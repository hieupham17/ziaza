package org.example.datn.controller;

import org.example.datn.entity.ChatLieu;
import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.KhuyenMai;
import org.example.datn.exception.DuplicatedException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.request.KhuyenMaiCreateUpdateRequest;
import org.example.datn.model.request.KhuyenMaiQuery;
import org.example.datn.model.request.KhuyenMaiRequest;
import org.example.datn.processor.ChatLieuProcessor;
import org.example.datn.processor.DanhMucProcessor;
import org.example.datn.processor.KhuyenMaiProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin("*")
@RestController("KhuyenMaiApi")
@RequestMapping("/khuyen-mai")
public class KhuyenMaiController {

    @Autowired
    KhuyenMaiProcessor processor;

    @GetMapping
    public ResponseEntity<ServiceResult> getList() {
        return ResponseEntity.ok(processor.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.findById(id));
    }

    @PostMapping()
    public ResponseEntity<ServiceResult> add(@RequestBody @Valid KhuyenMai khuyenMai) {
        return ResponseEntity.status(201).body(processor.save(khuyenMai));
    }

    @PutMapping("{id}")
    public ResponseEntity<ServiceResult> update(@PathVariable Long id, @RequestBody @Valid KhuyenMai khuyenMai) {
        khuyenMai.setId(id); // Đặt ID để cập nhật
        return ResponseEntity.ok(processor.save(khuyenMai));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ServiceResult> delete(@PathVariable Long id) {
        return ResponseEntity.ok(processor.delete(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceResult> create(@RequestBody KhuyenMaiCreateUpdateRequest request, UserAuthentication ua) throws DuplicatedException {
        return ResponseEntity.ok(processor.create(request, ua));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceResult> create(@PathVariable Long id, @RequestBody KhuyenMaiCreateUpdateRequest request, UserAuthentication ua) throws DuplicatedException {
        return ResponseEntity.ok(processor.update(id, request, ua));
    }

    @PostMapping("/get-list")
    public ResponseEntity<ServiceResult> findByKeywordAndLoai(@RequestBody KhuyenMaiQuery request) {
        return ResponseEntity.ok(processor.findByKeywordAndLoai(request));
    }

    @GetMapping("/get-list-by-user")
    public ResponseEntity<ServiceResult> getAllUserId(UserAuthentication ua) {
        return ResponseEntity.ok(processor.getAllUserId(ua));
    }
}

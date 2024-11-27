package org.example.datn.controller;

import org.example.datn.entity.DanhMuc;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.processor.DanhMucProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/rest/danhmuc")
public class DanhMucController {

    @Autowired
    private DanhMucProcessor danhMucProcessor;

    // Lấy danh sách tất cả các danh mục
    @GetMapping
    public ResponseEntity<ServiceResult> findAll() {
        ServiceResult result = danhMucProcessor.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Lấy danh mục theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        ServiceResult result = danhMucProcessor.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Tạo mới danh mục với thông tin UserAuthentication
    @PostMapping
    public ResponseEntity<ServiceResult> create(@RequestBody @Valid DanhMuc request, UserAuthentication ua) {
        ServiceResult result = danhMucProcessor.save(request, ua);
        return new ResponseEntity<>(result, HttpStatus.CREATED); // HttpStatus.CREATED cho response 201
    }

    // Cập nhật danh mục theo ID với thông tin UserAuthentication
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult> update(@PathVariable Long id, @RequestBody @Valid DanhMucRequest request, UserAuthentication ua) {
        ServiceResult result = danhMucProcessor.update(id, request, ua);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Xóa danh mục theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult> delete(@PathVariable Long id) {
        ServiceResult result = danhMucProcessor.delete(id);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }
}

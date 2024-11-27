package org.example.datn.controller;

import org.example.datn.entity.DanhMuc;
import org.example.datn.entity.Size;
import org.example.datn.entity.YeuCauDoiTra;
import org.example.datn.exception.DuplicatedException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.enums.LoaiYeuCau;
import org.example.datn.model.request.CancelOrderRequest;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.request.YeuCauDoiTraRequest;
import org.example.datn.model.response.SanPhamModel;
import org.example.datn.model.response.YeuCauDoiTraModel;
import org.example.datn.processor.SanPhamProcessor;
import org.example.datn.processor.YeuCauDoiTraProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/yeu-cau")
public class YeuCauDoiTraController {

    @Autowired
    YeuCauDoiTraProcessor processor;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResult> getAll() {
        return ResponseEntity.ok(processor.getAll());
    }

    @PostMapping
    public ResponseEntity<ServiceResult> create(@RequestBody YeuCauDoiTra request) {
        ServiceResult result = processor.save(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED); // Thay đổi từ HttpStatus.OK sang HttpStatus.CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult> update(@PathVariable Long id, @RequestBody YeuCauDoiTra request) {
        ServiceResult result = processor.update(id, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult> delete(@PathVariable Long id) {
        return ResponseEntity.ok(processor.delete(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<ServiceResult> getByLoaiAndTrangThai(
            @RequestParam LoaiYeuCau loai,
            @RequestParam Integer trangThai) {
        return ResponseEntity.ok(processor.getByLoaiAndTrangThai(loai, trangThai));
    }

    @GetMapping("/doitra")
    public ResponseEntity<ServiceResult> getByLoai(
            @RequestParam LoaiYeuCau loai) {
        return ResponseEntity.ok(processor.getByLoai(loai));
    }

    // API cập nhật trạng thái của yêu cầu đổi trả
    @PutMapping("/{id}/update-status")
    public ServiceResult updateStatus(@PathVariable("id") Long id, @RequestBody UserAuthentication ua) {
        return processor.updateStatus(id, ua);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<ServiceResult> cancelOrder(@PathVariable Long id, @RequestBody CancelOrderRequest request, UserAuthentication ua) throws IOException, InterruptedException {
        return ResponseEntity.ok(processor.cancelOrder(id, request, ua));
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceResult> create(@RequestParam("request") String request,
                                                @RequestParam("files") MultipartFile[] files, UserAuthentication ua) {
        return ResponseEntity.ok(processor.create(request, files, ua));
    }
}

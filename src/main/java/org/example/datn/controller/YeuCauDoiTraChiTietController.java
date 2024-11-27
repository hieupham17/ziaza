package org.example.datn.controller;

import org.example.datn.entity.YeuCauDoiTra;
import org.example.datn.entity.YeuCauDoiTraChiTiet;
import org.example.datn.model.ServiceResult;
import org.example.datn.processor.YeuCauDoiTraChiTietProcessor;
import org.example.datn.processor.YeuCauDoiTraProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/yeu-cau-chi-tiet")
public class YeuCauDoiTraChiTietController {

    @Autowired
    YeuCauDoiTraChiTietProcessor processor;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResult> getAll() {
        return ResponseEntity.ok(processor.getAll());
    }

    @PostMapping
    public ResponseEntity<ServiceResult> create(@RequestBody YeuCauDoiTraChiTiet request) {
        ServiceResult result = processor.save(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED); // Thay đổi từ HttpStatus.OK sang HttpStatus.CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult> update(@PathVariable Long id, @RequestBody YeuCauDoiTraChiTiet request) {
        ServiceResult result = processor.update(id, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult> delete(@PathVariable Long id) {
        return ResponseEntity.ok(processor.delete(id));
    }

    @GetMapping("/by-yeu-cau/{idDoiTra}")
    public ResponseEntity<ServiceResult> getByYeuCauDoiTra(@PathVariable Long idDoiTra) {
        return ResponseEntity.ok(processor.getByYeuCauDoiTra(idDoiTra));
    }

}

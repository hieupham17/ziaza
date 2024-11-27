package org.example.datn.controller;

import org.example.datn.entity.SanPham;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.response.SanPhamModel;
import org.example.datn.processor.SanPhamProcessor;
import org.example.datn.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("SanPhamApi")
@RequestMapping("/san-pham")
public class SanPhamController {

    @Autowired
    SanPhamProcessor processor;

    @Autowired
    SanPhamService service;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @GetMapping
    public ResponseEntity<ServiceResult> getAll() {
        return ResponseEntity.ok(processor.getAll());
    }

    @PostMapping
    public ResponseEntity<ServiceResult> add(@RequestParam("file") MultipartFile file, @ModelAttribute SanPhamModel model, UserAuthentication ua) {
        return ResponseEntity.ok(processor.save(model, file,ua));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult> update(@PathVariable Long id,
                                                @RequestParam(value = "file", required = false) MultipartFile file,
                                                @ModelAttribute SanPhamModel model,
                                                UserAuthentication ua) {
        // Kiểm tra nếu có file ảnh mới
        if (file != null && !file.isEmpty()) {
            // Nếu có ảnh mới, xử lý ảnh và cập nhật thông tin sản phẩm
            return ResponseEntity.ok(processor.update(id, model, file,ua));
        } else {
            // Nếu không có ảnh mới, chỉ cập nhật thông tin sản phẩm mà không thay đổi ảnh
            return ResponseEntity.ok(processor.update(id, model,ua));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceResult> updateStatus(@PathVariable Long id, @RequestParam("status") int status, UserAuthentication ua) {
        return ResponseEntity.ok(processor.updateStatus(id, status,ua));
    }

    @GetMapping("/search")
    public List<SanPham> searchProducts(@RequestParam String ten) {
        return service.searchProductsByName(ten);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult> delete(@PathVariable Long id) {
        return ResponseEntity.ok(processor.delete(id));
    }

}

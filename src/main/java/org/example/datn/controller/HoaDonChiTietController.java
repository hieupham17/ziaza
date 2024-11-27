package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.HoaDonChiTietRequest;
import org.example.datn.processor.HoaDonChiTietProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("HoaDonChiTietApi")
@RequestMapping("/hoa-don-chi-tiet")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoaDonChiTietController {

    @Autowired
    HoaDonChiTietProcessor processor;
    @PostMapping("/get-list-by-status")
    public ResponseEntity<ServiceResult> getListByStatus(@RequestBody HoaDonChiTietRequest request, UserAuthentication ua) {
        return ResponseEntity.ok(processor.getListByStatus(request, ua));
    }
    @GetMapping
    public ResponseEntity<ServiceResult> getAll() {
        return ResponseEntity.ok(processor.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @GetMapping("/get-id-hoa-don")
    public ServiceResult getByIdHoaDon(@RequestParam Long idHoaDon) {
        return processor.getByIdHoaDon(idHoaDon);
    }
}

package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.exception.AccessDeniedException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.DiaChiGiaoHangRequest;
import org.example.datn.processor.DiaChiGiaoHangProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController("DiaChiGiaoHangApi")
@RequestMapping("/dia-chi")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiaChiGiaoHangController {
    @Autowired
    DiaChiGiaoHangProcessor processor;

    @GetMapping("/get-list")
    public ResponseEntity<ServiceResult> getList(UserAuthentication ua) {
        return ResponseEntity.ok(processor.findByIdNguoiDung(ua));
    }

    @GetMapping("/get-active")
    public ResponseEntity<ServiceResult> getActive(UserAuthentication ua) {
        return ResponseEntity.ok(processor.getActive(ua));
    }


    @PostMapping("/insert")
    public ResponseEntity<ServiceResult> insert(@RequestBody DiaChiGiaoHangRequest request, UserAuthentication ua) {
        return ResponseEntity.ok(processor.insert(request, ua));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult> deleteById(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(processor.deleteById(id));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ServiceResult> changeDefault(@PathVariable Long id) {
        return ResponseEntity.ok(processor.changeDefault(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult> update(@PathVariable Long id, @RequestBody DiaChiGiaoHangRequest request) {
        return ResponseEntity.ok(processor.update(id, request));
    }



}

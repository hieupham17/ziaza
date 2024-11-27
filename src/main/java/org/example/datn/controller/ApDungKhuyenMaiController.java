package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.ApDungKhuyenMaiRequest;
import org.example.datn.processor.ApDungKhuyenMaiProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ApDungKhuyenMaiApi")
@RequestMapping("/ap-dung-khuyen-mai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApDungKhuyenMaiController {
    @Autowired
    ApDungKhuyenMaiProcessor processor;

    @PutMapping("/change")
    public ResponseEntity<ServiceResult> change(@RequestParam Long idKhuyenMai, UserAuthentication ua) {
        return ResponseEntity.ok(processor.change(idKhuyenMai, ua));
    }

    @GetMapping
    public ResponseEntity<ServiceResult> getList() {
        return ResponseEntity.ok(processor.getList());
    }

}

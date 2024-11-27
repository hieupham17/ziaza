package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.ServiceResult;
import org.example.datn.processor.PhuongThucVanChuyenProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("PhuongThucVanChuyenApi")
@RequestMapping("/phuong-thuc-van-chuyen")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhuongThucVanChuyenController {

    @Autowired
    PhuongThucVanChuyenProcessor processor;

    @GetMapping("/get-active")
    public ResponseEntity<ServiceResult> getActive() {
        return ResponseEntity.ok(processor.getActive());
    }
}

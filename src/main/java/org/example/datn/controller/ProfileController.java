package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.ProfileRequest;
import org.example.datn.processor.ProfileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController("ProfileApi")
@RequestMapping("/profile")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {
    @Autowired
    ProfileProcessor processor;

    @PutMapping("/update")
    public ResponseEntity<ServiceResult> update(@Valid @RequestParam("request") String request, @RequestPart(value = "file", required = false) MultipartFile file, UserAuthentication ua) {
        return ResponseEntity.ok(processor.update(request,file, ua));
    }
}
package org.example.datn.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.enums.UserRoles;
import org.example.datn.model.enums.UserStatus;
import org.example.datn.model.request.UserQuery;
import org.example.datn.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("UserApi")
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    @Autowired
    UserProcessor processor;

    @GetMapping("/get")
    public ResponseEntity<ServiceResult> get(UserAuthentication ua) {
        return ResponseEntity.ok(processor.get(ua));
    }

    @GetMapping("/get-list-by-role")
    public ResponseEntity<ServiceResult> getListByRole(@RequestParam UserRoles role) {
        return ResponseEntity.ok(processor.getListByRole(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.deleteById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<ServiceResult> getAll() {
        return ResponseEntity.ok(processor.getAll());
    }

    @PostMapping("/get-list")
    public ResponseEntity<ServiceResult> getList(@RequestBody UserQuery request) {
        return ResponseEntity.ok(processor.getList(request));
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<ServiceResult> changeStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        return ResponseEntity.ok(processor.changeStatus(id, status));
    }

}

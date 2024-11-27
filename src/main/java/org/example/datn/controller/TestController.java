//package org.example.datn.controller;
//
//import org.example.datn.entity.DanhMuc;
//import org.example.datn.model.ServiceResult;
//import org.example.datn.model.UserAuthentication;
//import org.example.datn.model.request.DanhMucRequest;
//import org.example.datn.model.request.HoaDonRequest;
//import org.example.datn.processor.DanhMucProcessor;
//import org.example.datn.processor.HoaDonProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping(value = "/rest/hoadon")
//public class TestController {
//
//    @Autowired
//    private HoaDonProcessor processor;
//
//    // Lấy danh sách tất cả các hóa đơn
//    @GetMapping
//    public ResponseEntity<ServiceResult> findAll() {
//        ServiceResult result = processor.getAll();
//        return ResponseEntity.ok(result);
//    }
//
//    // Lấy theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
//        ServiceResult result = processor.getById(id);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    // Thêm mới hóa đơn
//    @PostMapping
//    public ResponseEntity<ServiceResult> add(@RequestBody HoaDonRequest request, HttpServletRequest httpServletRequest) {
//        // Giả sử UserAuthentication được đặt trong request bởi middleware
//        UserAuthentication ua = (UserAuthentication) httpServletRequest.getAttribute("userAuth");
//
//        // Thực hiện lưu hóa đơn mới
//        ServiceResult result = processor.save(request, ua);
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/save")
//    public ResponseEntity<ServiceResult> save(@RequestBody HoaDonRequest request, UserAuthentication ua) {
//        return ResponseEntity.ok(processor.save(request, ua));
//    }
//
//}

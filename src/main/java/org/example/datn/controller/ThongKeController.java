//package org.example.datn.controller;
//
//import org.example.datn.model.ServiceResult;
//import org.example.datn.model.UserAuthentication;
//import org.example.datn.model.request.HoaDonRequest;
//import org.example.datn.model.response.HoaDonModel;
//import org.example.datn.processor.HoaDonProcessor;
//import org.example.datn.processor.ThongKeProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping(value = "/thongke")
//public class ThongKeController {
//
//    @Autowired
//    ThongKeProcessor thongKeProcessor;
//
//    @GetMapping("/doanh-thu")
//    public ResponseEntity<ServiceResult> getThongKe() {
//        ServiceResult result = thongKeProcessor.getAll();
//        return ResponseEntity.ok(result);
//    }
//}

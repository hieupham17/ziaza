package org.example.datn.controller;

import org.example.datn.entity.DanhMuc;
import org.example.datn.exception.DuplicatedException;
import org.example.datn.model.ServiceResult;
import org.example.datn.model.UserAuthentication;
import org.example.datn.model.request.CancelOrderRequest;
import org.example.datn.model.request.DanhMucRequest;
import org.example.datn.model.request.HoaDonChiTietRequest;
import org.example.datn.model.request.HoaDonRequest;
import org.example.datn.model.response.HoaDonModel;
import org.example.datn.processor.DanhMucProcessor;
import org.example.datn.processor.HoaDonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/rest/hoadon")
public class HoaDonController {

    @Autowired
    private HoaDonProcessor processor;

    // Lấy danh sách tất cả các hóa đơn
    @GetMapping
    public ResponseEntity<ServiceResult> findAll() {
        ServiceResult result = processor.getAll();
        return ResponseEntity.ok(result);
    }

    // Lấy theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult> getById(@PathVariable Long id) {
        ServiceResult result = processor.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Thêm mới hóa đơn
    @PostMapping
    public ResponseEntity<ServiceResult> add(@RequestBody HoaDonRequest request, HttpServletRequest httpServletRequest) {
        // Giả sử UserAuthentication được đặt trong request bởi middleware
        UserAuthentication ua = (UserAuthentication) httpServletRequest.getAttribute("userAuth");

        // Thực hiện lưu hóa đơn mới
        ServiceResult result = processor.save(request, ua);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity<ServiceResult> save(@RequestBody HoaDonRequest request, UserAuthentication ua) {
        return ResponseEntity.ok(processor.save(request, ua));
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<ServiceResult> update(@PathVariable Long id, @RequestBody HoaDonRequest request,UserAuthentication ua) {
//        ServiceResult result = processor.update(id, request,ua);
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/get-by-status")
    public ResponseEntity<ServiceResult> getByTrangThai(@RequestParam Integer trangThai) {
        ServiceResult result = processor.getByTrangThai(trangThai);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<ServiceResult> updateByTrangThai(@PathVariable Long id,
                                                           UserAuthentication ua) throws DuplicatedException {
        ServiceResult result = processor.updateStatus(id, ua);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update-huy-tra/{id}")
    public ResponseEntity<ServiceResult> updateByTrangThaiHuyTra(@PathVariable Long id,
                                                                 UserAuthentication ua,
                                                                 @RequestBody String lyDoHuy) {
        ServiceResult result = processor.updateHuyHoaDon(id, ua, lyDoHuy);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get-list-by-status")
    public ResponseEntity<ServiceResult> getListByStatus(@RequestBody HoaDonChiTietRequest request, UserAuthentication ua) {
        return ResponseEntity.ok(processor.getListByStatus(request, ua));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<ServiceResult> cancelOrder(@PathVariable Long id, @RequestBody CancelOrderRequest request, UserAuthentication ua) throws IOException, InterruptedException {
        return ResponseEntity.ok(processor.cancelOrder(id, request, ua));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ServiceResult> get(@PathVariable Long id) {
        ServiceResult result = processor.get(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get-doi-tra")
    public ResponseEntity<ServiceResult> getDoiTra(UserAuthentication ua) {
        return ResponseEntity.ok(processor.getHoaDonDoiTra(ua));
    }


}

package org.example.datn.controller;

import org.example.datn.entity.DiemTichLuy;
import org.example.datn.service.DiemTichLuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diem-tich-luy")
public class DiemTichLuyController {

    @Autowired
    private DiemTichLuyService diemTichLuyService;

    // Thêm điểm tích lũy
    @PutMapping("/update")
    public DiemTichLuy addDiem(
            @RequestParam Long idNguoiDung,
            @RequestParam int diem
//            @RequestParam String lyDo,
//            @RequestParam Long nguoiTao
    ) {
        return diemTichLuyService.update(idNguoiDung, diem);
    }

    @PutMapping("/use-diem")
    public DiemTichLuy useDiem(
            @RequestParam Long idNguoiDung,
            @RequestParam int diemCanSuDung
//            @RequestParam String lyDo,
//            @RequestParam Long nguoiTao
    ) {
        return diemTichLuyService.useDiem(idNguoiDung, diemCanSuDung);
    }
}

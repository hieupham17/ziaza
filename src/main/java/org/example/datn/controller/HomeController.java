package org.example.datn.controller;

import org.example.datn.entity.SanPham;
import org.example.datn.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HomeController {
    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/")
    public String home(Model model) {
        return "customer/home/index";
    }

    @GetMapping("/tim-kiem")
    public String category() {
        return "customer/filter/filter";
    }
    @GetMapping("/staff")
    public String staff(){
        return "/staff/index";
    }
    @GetMapping("/qr")
    public String staffQr(){
        return "/staff/qr";
    }
}

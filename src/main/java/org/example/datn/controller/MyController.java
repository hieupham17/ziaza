package org.example.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/my")
    public String myPage() {
        return "customer/my/index";
    }
}

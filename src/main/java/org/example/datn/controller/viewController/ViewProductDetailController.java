package org.example.datn.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewProductDetailController {
    @GetMapping("/productDetail/{id}")
    public String productDetail() {
        return "/customer/productDetail/index";
    }
}

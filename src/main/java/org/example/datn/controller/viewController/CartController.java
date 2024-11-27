package org.example.datn.controller.viewController;

import lombok.RequiredArgsConstructor;
import org.example.datn.entity.GioHang;
import org.example.datn.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping()
public class CartController {

    @GetMapping("/cart")
    public String cart() {
        return "customer/cart/cart";
    }
    @Autowired
    private GioHangService gioHangService;



    @GetMapping("/checkout")
    public String checkout() {
        return "customer/onlineSell/checkout";
    }

    @GetMapping("/payment")
    public String payment() {
        return "customer/onlineSell/payment";
    }

    @GetMapping("/bill")
    public String bill() {
        return "customer/onlineSell/bill";
    }


}

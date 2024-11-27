package org.example.datn.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ViewWishlistController {
    @GetMapping("/wishlist")
    public String wishlist() {
        return "customer/wishlist/index";
    }
}

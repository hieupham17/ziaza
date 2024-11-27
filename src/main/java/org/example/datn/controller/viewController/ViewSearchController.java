package org.example.datn.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewSearchController {

        @GetMapping("/search")
        public String search() {
            return "customer/filter/filter";
        }
}

package com.fnbadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/page/index")
    public String index(){
        return "index.html";
    }

    @GetMapping("/page/product")
    public String product(){
        return "product.html";
    }

    @GetMapping("/page/coupon")
    public String coupon(){
        return "coupon.html";
    }

    @GetMapping("/page/order")
    public String order(){
        return "order.html";
    }

    @GetMapping("/page/member")
    public String member(){
        return "member.html";
    }
}

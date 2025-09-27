package com.fnbadmin.controller;

import com.fnbadmin.controller.request.CouponRequest;
import com.fnbadmin.controller.request.CreateCouponRequest;
import com.fnbadmin.controller.response.CouponInfoResponse;
import com.fnbadmin.controller.response.CouponListResponse;
import com.fnbadmin.controller.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupon")
    public String coupon(){
        return "coupon.html";
    }

    @GetMapping("/coupon/list")
    public ResponseEntity<List<CouponListResponse>> getCoupons(CouponRequest couponRequest) {
        return ResponseEntity.ok(this.couponService.getList(couponRequest));
    }

    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<CouponInfoResponse> getInfo(@PathVariable int couponId) {
        return ResponseEntity.ok(this.couponService.getInfo(couponId));
    }

    @PostMapping("/coupon")
    public ResponseEntity<Boolean> create(CreateCouponRequest createCouponRequest) {
        // Implementation for creating a new coupon would go here
        return ResponseEntity.ok(this.couponService.create(createCouponRequest));
    }
}

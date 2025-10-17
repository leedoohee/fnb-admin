package com.fnbadmin.controller;

import com.fnbadmin.controller.request.CouponRequest;
import com.fnbadmin.controller.request.CreateCouponRequest;
import com.fnbadmin.controller.response.CouponInfoResponse;
import com.fnbadmin.controller.response.CouponListResponse;
import com.fnbadmin.controller.response.OrderListResponse;
import com.fnbadmin.controller.response.PageResponse;
import com.fnbadmin.controller.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupon")
    public String coupon(){
        return "coupon.html";
    }

    @GetMapping("/coupon-statistics")
    public String couponStatistics(){
        return "coupon-statistics.html";
    }

    @GetMapping("/coupon/list")
    public ResponseEntity<PageResponse<CouponListResponse>> getCoupons(CouponRequest couponRequest) {
        return ResponseEntity.ok(this.couponService.getCoupons(couponRequest));
    }

    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<CouponInfoResponse> getInfo(@PathVariable int couponId) {
        return ResponseEntity.ok(this.couponService.getInfo(couponId));
    }

    @PostMapping("/coupon")
    public ResponseEntity<Boolean> create(CreateCouponRequest createCouponRequest) {
        return ResponseEntity.ok(this.couponService.create(createCouponRequest));
    }
}

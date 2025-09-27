package com.fnbadmin.controller.response;

import com.fnbadmin.domain.Coupon;
import com.fnbadmin.domain.CouponProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CouponInfoResponse {
    private Coupon coupon;
    private List<CouponProduct> products;
}

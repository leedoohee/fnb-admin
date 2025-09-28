package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequest {
    private String couponId;
    private String couponName;
    private String applyStartDate;
    private String applyEndDate;
    private String registerStartDate;
    private String registerEndDate;
    private String registerBy;
    private String status;
    private int page;
    private int pageLimit;
}

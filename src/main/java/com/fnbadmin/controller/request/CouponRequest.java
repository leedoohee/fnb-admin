package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouponRequest {
    private int page;
    private int pageLimit;
    private String couponId;
    private String couponName;
    private String applyStartDate;
    private String applyEndDate;
    private String registerStartDate;
    private String registerEndDate;
    private String registerBy;
    private List<String> status;
    private List<String> couponType;
    private String searchType;
    private String searchWord;
}

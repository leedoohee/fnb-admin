package com.fnbadmin.controller.response;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponListResponse {

    private int couponId;
    private String name;
    private String description;
    private String couponName;
    private String applyStartDate;
    private String applyEndDate;
    private String registerDate;
    private String registerBy;
    private String status;
}

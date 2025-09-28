package com.fnbadmin.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCouponResponse {
    private int id;
    private int memberId;
    private int couponId;
    private String isUsed;
    private int status;
    private String issuedAt;
    private String usedAt;
    private String createdAt;
    private String updatedAt;
}

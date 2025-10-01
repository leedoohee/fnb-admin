package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberListResponse {

    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String joinDate;
    private String memberGrade;
    private int points;
    private int ownedCouponCount;
    private int totalOrderCount;
    private int totalOrderAmount;
}

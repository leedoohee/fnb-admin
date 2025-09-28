package com.fnbadmin.controller.response;

import com.fnbadmin.domain.MemberCoupon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MemberInfoResponse {

    private int id;
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String joinDate;
    private String status;
    private int points;
    private String memberGrade;
    private String birthDate;
    private int ownedCouponCount;
    private int totalOrderCount;
    private int totalOrderAmount;
    private String lastLoginDate;
    private String lastLoginIp;
    private String grade;

    private List<MemberCoupon> memberCoupons;
}

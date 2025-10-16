package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class MemberGradeListResponse {
    private int memberGradeId;
    private String grade;
    private String description;
    private String discountType;
    private int discountRate;
    private BigDecimal minOrderAmount;
    private BigDecimal maxOrderAmount;
    private String createdAt;
    private String updatedAt;
}

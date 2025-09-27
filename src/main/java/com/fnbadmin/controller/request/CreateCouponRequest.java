package com.fnbadmin.controller.request;

import com.fnbadmin.domain.CouponProduct;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateCouponRequest {
    private int id;
    private String name;
    private String description;
    private String couponType; // site-wide, product-specific 등
    private String discountType; // percentage, fixed amount 등
    private String discountAmount;
    private Date applyStartDate;
    private Date applyEndDate;
    private BigDecimal minApplyPrice;// 최소 주문 금액 등
    private String memberShipGrades;
    private int availableQuantity;
    private int usedQuantity;
    private String status; // active, expired, used 등
    private String couponCode;
    private String issuedType; // 자동 발급, 수동 발급 등

    private List<CouponProduct> couponProducts;
}

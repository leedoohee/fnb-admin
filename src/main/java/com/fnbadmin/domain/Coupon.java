package com.fnbadmin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Coupon {

    @Id
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
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
    private String couponCode;
    private String issuedType; // 자동 발급, 수동 발급 등

    @Transient
    private List<CouponProduct> couponProducts;

    public Coupon() {

    }
}

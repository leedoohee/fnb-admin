package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderListResponse {

    private String orderId;
    private String memberName;
    private String orderStatus;
    private String orderDate;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String paymentDate;
    private String paymentType;
    private BigDecimal paymentAmount;
    private BigDecimal usePoint;
    private BigDecimal couponAmount;
    private BigDecimal discountAmount;
}

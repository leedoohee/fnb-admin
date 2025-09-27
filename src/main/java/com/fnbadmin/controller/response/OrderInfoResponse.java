package com.fnbadmin.controller.response;

import com.fnbadmin.domain.Order;
import com.fnbadmin.domain.OrderAdditionalOption;
import com.fnbadmin.domain.OrderProduct;
import com.fnbadmin.domain.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderInfoResponse {

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
    private List<OrderProduct> orderProducts;
    private Payment payment;

    @Transient
    List<OrderAdditionalOption> orderAdditionalOptions;
}

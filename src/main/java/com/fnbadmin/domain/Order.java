package com.fnbadmin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
public class Order {

    @Id
    private int id;
    private String orderId;
    private int memberId;
    private String orderDate;
    private String orderStatus;
    private BigDecimal totalAmount;
    private BigDecimal usePoint;
    private int orderType;
    private int couponAmount;
    private BigDecimal discountAmount;

    @Transient
    private List<OrderProduct> orderProducts;

    @Transient
    private Member member;

    @Transient
    private Payment payment;

    public Order() {

    }
}

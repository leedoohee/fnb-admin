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

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
public class OrderProduct {

    @Id
    private int id;
    private String orderProductId;
    private String orderId;
    private int productId;
    private String productName;
    private int quantity;
    private int optionId;
    private String optionName;
    private String optionPrice;
    private BigDecimal paymentAmount;
    private BigDecimal couponAmount;
    private BigDecimal discountAmount;
    private int couponId;

    @Transient
    private List<OrderAdditionalOption> orderAdditionalOptions;

    public OrderProduct() {

    }
}

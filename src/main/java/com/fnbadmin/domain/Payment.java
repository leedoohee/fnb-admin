package com.fnbadmin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Payment {

    @Id
    private int id;
    private String orderId;
    private String paymentDate;
    private String paymentType;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private BigDecimal paymentAmount;

    @Transient
    private List<PaymentElement> paymentElements;

    public Payment() {

    }
}

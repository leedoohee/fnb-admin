package com.fnbadmin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "order_master")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    // Foreign Key to the Member entity (consider using @ManyToOne for proper JPA relationship)
    @Column(name = "member_id", nullable = false)
    private int memberId;

    // Using LocalDateTime for precise date and time tracking
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "order_status")
    private String orderStatus;

    // Set precision/scale for BigDecimal to handle currency accurately
    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "use_point", precision = 19, scale = 2)
    private BigDecimal usePoint;

    @Column(name = "order_type")
    private int orderType;

    @Column(name = "coupon_amount")
    private int couponAmount;

    @Column(name = "discount_amount", precision = 19, scale = 2)
    private BigDecimal discountAmount;

    @Transient
    private List<OrderProduct> orderProducts;

    @Transient
    private Member member;

    @Transient
    private Payment payment;
}
package com.fnbadmin.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "coupon_type")
    private String couponType;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_amount")
    private String discountAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apply_start_at")
    private Date applyStartAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apply_end_at")
    private Date applyEndAt;

    @Column(name = "min_apply_price", precision = 19, scale = 2)
    private BigDecimal minApplyPrice;

    @Column(name = "member_ship_grades")
    private String memberShipGrades;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "used_quantity")
    private int usedQuantity;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "issued_type") // 자동 발급, 수동 발급
    private String issuedType;

    // This field is managed by the application, not persisted in the DB table
    @Transient
    private List<CouponProduct> couponProducts;

    public Coupon() {
        // Default constructor
    }
}
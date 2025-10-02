package com.fnbadmin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // Monetary field with defined precision/scale
    @Column(name = "price", precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "status")
    private String status;

    @Column(name = "min_quantity")
    private int minQuantity;

    @Column(name = "max_quantity")
    private int maxQuantity;

    @Column(name = "is_infinite_qty")
    private int isInfiniteQty;

    @Column(name = "product_type") // 1:일반, 2:세트
    private int productType;

    @Column(name = "sale_type") // 1:일반, 2:예약
    private int saleType;

    @Column(name = "is_take_out") // 1:포장, 0:매장
    private int isTakeOut;

    @Column(name = "is_delivery") // 1:배달, 0:매장
    private int isDelivery;

    @Column(name = "is_use") // 1:사용, 0:미사용
    private int isUse;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Transient
    private List<ProductOption> productOptions;

}
package com.fnbadmin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor // Required for JPA entities, especially with @Builder and @AllArgsConstructor
@Table(name = "order_additional_option")
public class OrderAdditionalOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    // Foreign Key or reference to the Order entity
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_product_id", nullable = false)
    private String orderProductId;

    // Reference to the AdditionalOption entity
    @Column(name = "additional_option_id", nullable = false)
    private String additionalOptionId;

    @Column(name = "additional_option_name")
    private String additionalOptionName;

    @Column(name = "price")
    private int price;
}
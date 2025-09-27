package com.fnbadmin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class OrderAdditionalOption {

    @Id
    private String id;
    private String orderId;
    private String orderProductId;
    private String additionalOptionName;
    private int price;

    public OrderAdditionalOption() {

    }
}

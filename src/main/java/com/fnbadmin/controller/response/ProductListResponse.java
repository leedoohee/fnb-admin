package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductListResponse {

    private int productId;
    private String name;
    private int price;
    private String status;
    private int productType; // 1:일반, 2:세트
    private int saleType; // 1:일반, 2:예약
    private String createdAt;
    private String updatedAt;
    private String isUse; // 1:사용, 0:미사용
}

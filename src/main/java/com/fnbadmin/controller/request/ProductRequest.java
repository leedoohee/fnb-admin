package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private int productId;
    private String productName;
    private int productType; // 1:일반, 2:세트
    private int saleType; // 1:일반, 2:예약
    private String status;
    private String startDate;
    private String endDate;
    private int page;
    private int pageLimit;
}

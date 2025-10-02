package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private int page;
    private int pageLimit;
    private int productId;
    private String productName;
    private int productType; // 1:일반, 2:세트
    private int saleType; // 1:일반, 2:예약
    private String status;
    private String registerStartDate;
    private String registerEndDate;
    private String searchType;
    private String searchWord;
}

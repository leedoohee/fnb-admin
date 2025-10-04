package com.fnbadmin.controller.response;

import com.fnbadmin.domain.ProductAttachFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductInfoResponse {
    private int id;
    private String name;
    private String description;
    private int price;
    private String status;
    private int minQuantity;
    private int maxQuantity;
    private int isInfiniteQty;
    private int productType; // 1:일반, 2:세트
    private int saleType; // 1:일반, 2:예약
    private int isTakeOut; // 1:포장, 0:매장
    private int isDelivery; // 1:배달, 0:매장
    private int isUse; // 1:사용, 0:미사용
    private int Quantity;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<ProductOptionResponse> productOptions;
    private List<ProductAttachFileResponse> attachFiles;

}

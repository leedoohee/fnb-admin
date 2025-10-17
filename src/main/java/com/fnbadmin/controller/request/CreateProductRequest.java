package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CreateProductRequest {

    private int id;
    private int payType;
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
    private String isUsed; // 1:사용, 0:미사용
    private int Quantity;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<CreateProductOptionRequest> options;
}

package com.fnbadmin.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProductOptionRequest {

    private int productId;
    private String name;
    private int price;
    private int isUse;
}

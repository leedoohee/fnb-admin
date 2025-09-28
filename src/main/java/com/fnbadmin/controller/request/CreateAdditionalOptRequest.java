package com.fnbadmin.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateAdditionalOptRequest {
    private int productId;
    private String name;
    private int price;
    private int isUse; // 1:사용, 0:미사용
}

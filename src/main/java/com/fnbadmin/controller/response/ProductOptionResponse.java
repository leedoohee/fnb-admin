package com.fnbadmin.controller.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductOptionResponse {
    private int optionId;
    private String optionType;
    private String optionGroupId;
    private String name;
    private int price;
    private String createdAt;
    private String updatedAt;
}

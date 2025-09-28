package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdditionalOptionResponse {

    private int id;
    private String name;
    private int price;
    private String createdAt;
    private String updatedAt;
}

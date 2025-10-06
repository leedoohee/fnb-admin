package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OptionGroupInfoResponse {
    private String optionGroupId;
    private String name;
    private String description;
    private boolean isUsed;
    private int price;
}

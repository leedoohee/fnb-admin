package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private int orderId;
    private String memberName;
    private String orderStatus;
    private String startDate;
    private String endDate;
    private int page;
    private int pageLimit;
}

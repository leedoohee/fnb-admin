package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private int page;
    private int pageLimit;
    private int orderId;
    private String memberName;
    private String orderStatus;
    private String orderStartDate;
    private String orderEndDate;
    private String searchType;
    private String searchWord;
}

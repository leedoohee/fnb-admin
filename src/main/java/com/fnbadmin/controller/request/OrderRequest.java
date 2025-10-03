package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private int page;
    private int pageLimit;
    private int orderId;
    private int memberSeq;
    private String memberName;
    private List<String> orderStatus;
    private List<Integer> orderType;
    private String orderStartDate;
    private String orderEndDate;
    private String searchType;
    private String searchWord;
}

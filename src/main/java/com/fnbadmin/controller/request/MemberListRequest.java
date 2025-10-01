package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListRequest {
    private int page;
    private int pageLimit;
}

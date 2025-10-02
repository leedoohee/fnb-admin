package com.fnbadmin.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberListRequest {
    private int page;
    private int pageLimit;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memberGrade;
    private String searchType;
    private String searchWord;
}

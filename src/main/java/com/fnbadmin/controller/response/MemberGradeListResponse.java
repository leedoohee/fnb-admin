package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberGradeListResponse {
    private int id;
    private String grade;
    private String description;
    private String discountType;
    private int discountRate;
    private String createdAt;
    private String updatedAt;
}

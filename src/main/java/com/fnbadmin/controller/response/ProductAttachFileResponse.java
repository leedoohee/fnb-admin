package com.fnbadmin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductAttachFileResponse {
    private int productId;
    private String fileName;
    private String filePath;
    private String createdAt;
    private String updatedAt;
}

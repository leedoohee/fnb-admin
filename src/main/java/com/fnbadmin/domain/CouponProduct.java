package com.fnbadmin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class CouponProduct {

    @Id
    private int id;
    private int couponId;
    private int productId;
    private String productName;
    private String isApplyTotalProduct; //전체상품적용여부
    //사용제한 갯수, 적용여부 검토
    private int quantity;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public CouponProduct() {

    }
}

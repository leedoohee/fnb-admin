package com.fnbadmin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

    @Id
    private int id;
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String joinDate;
    private String status;
    private int points;
    private String memberGrade;
    private String birthDate;
    private int ownedCouponCount;
    private String lastLoginDate;
    private String lastLoginIp;

    public Member() {

    }
}

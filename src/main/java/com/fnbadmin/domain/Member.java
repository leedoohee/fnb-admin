package com.fnbadmin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor // Satisfies JPA requirement for a default constructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "member_id", unique = true, nullable = false)
    private String memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true) // Email is often unique
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    // Recommended: Use LocalDate or LocalDateTime for date/time fields
    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "status")
    private String status;

    @Column(name = "points")
    private int points;

    // Changed to 'grade' or kept 'memberGrade' for clarity. Keeping both per original.
    @Column(name = "member_grade")
    private String memberGrade;

    @Column(name = "birth_date")
    private LocalDate birthDate; // Use LocalDate for birth date

    @Column(name = "owned_coupon_count")
    private int ownedCouponCount;

    @Column(name = "total_order_count")
    private int totalOrderCount;

    @Column(name = "total_order_amount")
    private int totalOrderAmount;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate; // Use LocalDateTime for exact time

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "grade") // Duplicative of memberGrade, but kept per original
    private String grade;

    // This relationship is handled by the application, not mapped to a column
    @Transient
    private List<MemberCoupon> memberCoupons;
}
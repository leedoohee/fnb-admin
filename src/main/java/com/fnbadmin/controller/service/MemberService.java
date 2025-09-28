package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.MemberRepository;
import com.fnbadmin.controller.response.MemberGradeListResponse;
import com.fnbadmin.controller.response.MemberInfoResponse;
import com.fnbadmin.domain.Member;
import com.fnbadmin.domain.MemberCoupon;
import com.fnbadmin.domain.MemberGrade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberInfoResponse getInfo(String memberId) {

        Member member = this.memberRepository.findMemberById(memberId);
        List<MemberCoupon> memberCoupons = this.memberRepository.findMemberCoupons(member.getId());

        return MemberInfoResponse.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .joinDate(String.valueOf(member.getJoinDate()))
                .status(member.getStatus())
                .points(member.getPoints())
                .memberGrade(member.getMemberGrade())
                .birthDate(String.valueOf(member.getBirthDate()))
                .ownedCouponCount(member.getOwnedCouponCount())
                .totalOrderCount(member.getTotalOrderCount())  //batch
                .totalOrderAmount(member.getTotalOrderAmount()) //batch
                .lastLoginDate(String.valueOf(member.getLastLoginDate()))
                .lastLoginIp(member.getLastLoginIp())
                .grade(member.getGrade())
                .memberCoupons(memberCoupons)
                .build();
    }

    public List<MemberGradeListResponse> getAllGrades() {
        List<MemberGradeListResponse> responses = new ArrayList<>();
        List<MemberGrade> memberGrades = this.memberRepository.findAllMemberGrades();

        for (MemberGrade grade : memberGrades) {
            responses.add(MemberGradeListResponse.builder()
                    .id(grade.getId())
                    .grade(grade.getGrade())
                    .description(grade.getDescription())
                    .discountType(grade.getDiscountType())
                    .discountRate(grade.getDiscountRate())
                    .build());
        }

        return responses;
    }
}

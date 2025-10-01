package com.fnbadmin.controller;

import com.fnbadmin.controller.request.MemberListRequest;
import com.fnbadmin.controller.response.MemberGradeListResponse;
import com.fnbadmin.controller.response.MemberInfoResponse;
import com.fnbadmin.controller.response.MemberPageResponse;
import com.fnbadmin.controller.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public String member() {
        return "member.html";
    }

    @GetMapping("/member-grade")
    public String memberGrade() {
        return "member-grade.html";
    }

    @GetMapping("/member/list")
    public ResponseEntity<MemberPageResponse> getMembers(MemberListRequest memberListRequest) {
        return ResponseEntity.ok(this.memberService.getList(memberListRequest));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberInfoResponse> getInfo(@PathVariable String memberId) {
        return ResponseEntity.ok(this.memberService.getInfo(memberId));
    }

    @GetMapping("/member-grade/list")
    public ResponseEntity<List<MemberGradeListResponse>> getGradeList() {
        return ResponseEntity.ok(this.memberService.getAllGrades());
    }
}

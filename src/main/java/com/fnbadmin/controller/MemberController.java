package com.fnbadmin.controller;

import com.fnbadmin.controller.response.MemberGradeListResponse;
import com.fnbadmin.controller.response.MemberInfoResponse;
import com.fnbadmin.controller.service.MemberService;
import com.fnbadmin.domain.MemberGrade;
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

    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberInfoResponse> getInfo(@PathVariable String memberId) {
        return ResponseEntity.ok(this.memberService.getInfo(memberId));
    }

    @GetMapping("/member/grade/list")
    public ResponseEntity<List<MemberGradeListResponse>> getGradeList() {
        return ResponseEntity.ok(this.memberService.getAllGrades());
    }
}

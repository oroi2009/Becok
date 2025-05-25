package com.likelion.demo.domain.member.web.controller;

import com.likelion.demo.domain.member.service.MemberService;
import com.likelion.demo.domain.member.web.dto.*;
import com.likelion.demo.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //목표 입력
    @PostMapping("/goal/{memberId}")
    public ResponseEntity<SuccessResponse<?>> createGoal(@RequestBody @Valid CreateGoalReq createGoalReq,@PathVariable Long memberId) {
        CreateGoalRes res = memberService.createGoal(createGoalReq,memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signup(@RequestBody @Valid SignupReq signupReq) {
        memberService.signup(signupReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<?>> login(@RequestBody @Valid LoginReq loginReq) {
        LoginRes loginRes = memberService.login(loginReq);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(memberService.login(loginReq)));
    }

    //세부정보 입력
    @PostMapping("/profile/{memberId}")
    public ResponseEntity<SuccessResponse<?>> createProfile(@RequestBody @Valid CreateProfileReq createProfileReq, @PathVariable Long memberId) {
        CreateProfileRes res = memberService.createProfile(createProfileReq,memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

}

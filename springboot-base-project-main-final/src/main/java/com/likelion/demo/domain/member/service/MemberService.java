package com.likelion.demo.domain.member.service;

import com.likelion.demo.domain.member.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


public interface MemberService {

    //목표 입력
    CreateGoalRes createGoal(CreateGoalReq createGoalReq, Long memberId);

    //회원가입
    void signup(SignupReq signupReq);

    //로그인
    LoginRes login(LoginReq loginReq);

    //상세정보 입력
    CreateProfileRes createProfile(CreateProfileReq createProfileReq, Long memberId);
}

package com.likelion.demo.domain.member.service;

import com.likelion.demo.domain.member.web.dto.CreateGoalReq;
import com.likelion.demo.domain.member.web.dto.CreateGoalRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


public interface MemberService {

    //목표 입력
    CreateGoalRes createGoal(CreateGoalReq createGoalReq, Long memberId);
}

package com.likelion.demo.domain.member.service;

import com.likelion.demo.domain.member.entity.Member;
import com.likelion.demo.domain.member.exception.MemberNotFoundException;
import com.likelion.demo.domain.member.repository.MemberRepository;
import com.likelion.demo.domain.member.web.dto.CreateGoalReq;
import com.likelion.demo.domain.member.web.dto.CreateGoalRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImple implements MemberService {
    private final MemberRepository memberRepository;


    //목표 입력
    @Override
    public CreateGoalRes createGoal(CreateGoalReq createGoalReq,Long memberId) {
        //멤버 id를 확인하여 멤버 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        //멤버의 목표 저장
        member.setGoal(createGoalReq.getGoal());
        //DB에 목표 내용 저장
        memberRepository.save(member);

        return new CreateGoalRes(member.getId());
    }
}

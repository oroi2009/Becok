package com.likelion.demo.domain.member.service;

import com.likelion.demo.domain.member.entity.Interest;
import com.likelion.demo.domain.member.entity.Member;
import com.likelion.demo.domain.member.entity.MemberInterest;
import com.likelion.demo.domain.member.exception.EmailDuplicateException;
import com.likelion.demo.domain.member.exception.MemberNotFoundException;
import com.likelion.demo.domain.member.exception.PasswordMismatchException;
import com.likelion.demo.domain.member.repository.InterestRepository;
import com.likelion.demo.domain.member.repository.MemberRepository;
import com.likelion.demo.domain.member.web.dto.*;
import com.likelion.demo.domain.participation.entity.ProgramRecord;
import com.likelion.demo.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImple implements MemberService {
    private final MemberRepository memberRepository;
    private final InterestRepository interestRepository;

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


    //회원 가입
    @Override
    public void signup(SignupReq signupReq) {

        if (memberRepository.existsByEmail(signupReq.getEmail())) {
            throw new EmailDuplicateException();
        }

        if (!signupReq.getPassword().equals(signupReq.getPasswordCheck())) {
            throw new PasswordMismatchException();
        }

        Member member = Member.builder()
                .email(signupReq.getEmail())
                .password(signupReq.getPassword())
                .build();

        memberRepository.save(member);
    }

    //상세정보 입력
    @Override
    public CreateProfileRes createProfile(CreateProfileReq createProfileReq, Long memberId) {
        //멤버 id를 확인하여 멤버 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        //리스트를 스트림으로 바꿔 반복 처리 준비
        List<MemberInterest> memberInterestList = createProfileReq.getInterests().stream()
                .map(interestType -> {
                    //DB에서 해당 관심 분야 존재 확인
                    Interest interest = interestRepository.findByInterestType(interestType)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관심 분야: " + interestType));
                    //Intrest와 Member를 연결하는 MemberIntrest 객체 생성
                    return MemberInterest.builder()
                            .member(member)
                            .interest(interest)
                            .build();
                })
                .collect(Collectors.toList()); //앞서 생성된 객체들을 리스트로 모아서 저장

        //String으로 받아온 참여 내역 파싱
        String rawParticipation = createProfileReq.getJoinedPrograms(); // 예: "452024학년도...\n다른 프로그램..."
        if (rawParticipation != null && !rawParticipation.isBlank()) {
            //"\n"을 기준으로 나눠서 lines 배열에 저장
            String[] lines = rawParticipation.split("\\n");
            //"2025-02-14" 같은 날짜를 감지
            Pattern datePattern = Pattern.compile("(20\\d{2}-\\d{2}-\\d{2})");

            for (String line : lines) {
                Matcher matcher = datePattern.matcher(line);
                // 날짜가 있으면 파싱 처리 -> '없음'이면 실행하지 않는다.
                if (matcher.find()) {
                    //날짜 문자열 추출 -> "2025-02-14"
                    String dateStr = matcher.group(1);
                    //LocalDate 타입으로 변환
                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    int dateIndex = matcher.start();
                    //날짜가 시작되기 전까지의 문자열을 제목으로 간주
                    String title = line.substring(0, dateIndex).trim();

                    ProgramRecord record = new ProgramRecord();
                    record.setTitle(title);
                    record.setParticipationDate(date);

                    // 양방향 연관관계 설정
                    member.addProgramRecord(record);
                }
            }
        }

        //JPA의 orphanRemoval 적용을 피하기 위함
        member.getMemberInterestList().clear();

        //멤버 상세정보 저장
        member.setGrade(createProfileReq.getGrade());
        member.setSemester(createProfileReq.getSemester());
        member.setProgramPoint(createProfileReq.getProgramPoint());
        member.getMemberInterestList().addAll(memberInterestList); //기존 컬렉션 유지 -> 내부만 바꾸는 방식
        member.setRecommendType(createProfileReq.getRecommendType());

        memberRepository.save(member);
        return new CreateProfileRes(member.getId());
    }


}

package com.likelion.demo.domain.recommendation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.demo.domain.bookmark.repository.ContestBookmarkRepository;
import com.likelion.demo.domain.bookmark.repository.ProgramBookmarkRepository;
import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.entity.enums.ContestStatus;
import com.likelion.demo.domain.contest.exception.ContestNotFoundException;
import com.likelion.demo.domain.contest.repository.ContestRepository;
import com.likelion.demo.domain.member.entity.Member;
import com.likelion.demo.domain.member.exception.MemberNotFoundException;
import com.likelion.demo.domain.member.repository.MemberRepository;
import com.likelion.demo.domain.notification.repository.ContestNotificationRepository;
import com.likelion.demo.domain.notification.repository.ProgramNotificationRepository;
import com.likelion.demo.domain.programData.entity.Program;
import com.likelion.demo.domain.programData.repository.ProgramRepository;
import com.likelion.demo.domain.programData.web.dto.RoadmapProgramRes;
import com.likelion.demo.domain.recommendation.engin.GptClient;
import com.likelion.demo.domain.recommendation.engin.GptPromptBuilder;
import com.likelion.demo.domain.recommendation.entity.RecommendContest;
import com.likelion.demo.domain.recommendation.entity.RecommendProgram;
import com.likelion.demo.domain.recommendation.exception.RecommendNotFoundException;
import com.likelion.demo.domain.recommendation.exception.ListProgramNotFoundException;
import com.likelion.demo.domain.recommendation.repository.RecommendContestRepository;
import com.likelion.demo.domain.recommendation.repository.RecommendProgramRepository;
import com.likelion.demo.domain.recommendation.web.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GptRecommendationServiceImpl implements GptRecommendationService {
    private final RecommendProgramRepository recommendProgramRepository;
    private final RecommendContestRepository recommendContestRepository;
    private final MemberRepository memberRepository;
    private final ContestBookmarkRepository contestBookmarkRepository;
    private final ProgramRepository programRepository;
    private final GptPromptBuilder gptPromptBuilder;
    private final GptClient gptClient;
    private final ContestRepository contestRepository;
    private final ProgramBookmarkRepository bookmarkRepository;
    private final ProgramNotificationRepository notificationRepository;
    private final ContestNotificationRepository contestNotificationRepository;

    @Transactional
    @Override
    public void RecommendContest(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<Contest> contestList = contestRepository.findAll();
        List<GetRecommendationContestReq> reqList = new ArrayList<>();

        for (Contest contest : contestList) {
            GetRecommendationContestReq req = GetRecommendationContestReq.from(contest);
            reqList.add(req);
        }

        String prompt = gptPromptBuilder.createPromptForContest(member, reqList);

        String response = gptClient.requestRecommendation(prompt);
        System.out.println(response);

        ObjectMapper mapper = new ObjectMapper();
        String content = "";

        try {
            JsonNode rootNode = mapper.readTree(response);
            content = rootNode.path("choices").get(0).path("message").path("content").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            content = "";
        }

        List<Long> recommendedIds = Arrays.stream(content.split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .filter(s -> s.matches("\\d+"))
                .map(Long::parseLong)  // 문자열 → Long
                .collect(Collectors.toList());


        for (Long contestId : recommendedIds) {
            Contest contest = contestRepository.findById(contestId)
                    .orElseThrow(ContestNotFoundException::new);

            RecommendContest recommendContest = RecommendContest.builder()
                    .member(member)
                    .contest(contest)
                    .title(contest.getName())
                    .build();
            recommendContestRepository.save(recommendContest);
        }
    }

    @Override
    public List<RecommendContestRes> getRecommendContest(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<RecommendContest> recommendContestList = recommendContestRepository.findAllByMemberId(memberId);

        List<RecommendContestRes> resList = new ArrayList<>();

        for (RecommendContest rc : recommendContestList) {
            Contest contest = rc.getContest();

            boolean bookmarked = contestBookmarkRepository.existsByMemberIdAndContestId(memberId, contest.getId());
            boolean notification = contestNotificationRepository.existsByMemberIdAndContestId(memberId, contest.getId());

            // status 계산
            LocalDate today = LocalDate.now();
            if (today.isBefore(contest.getStartDate())) {
                contest.setStatus(ContestStatus.UPCOMING);
            } else if (!today.isAfter(contest.getEndDate())) {
                contest.setStatus(ContestStatus.ONGOING);
            } else {
                contest.setStatus(ContestStatus.CLOSED);
            }

            // 카테고리 파싱
            String categoryStr = contest.getCategory();
            List<String> categories = (categoryStr != null) ?
                    Arrays.stream(categoryStr.split("/"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList()) :
                    new ArrayList<>();

            // 응답 객체 생성
            RecommendContestRes res = RecommendContestRes.of(
                    contest,
                    contest.getStatus(), // status 변수 대신 바로 가져오기
                    categories,
                    bookmarked,
                    notification
            );
            resList.add(res);
        }
        return resList;
    }

    @Transactional
    @Override
    public GptRecommendationProgramRes RecommendProgram(Long memberId) {
        String result;
        //멤버 id를 확인하여 멤버 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        // 현재 진행 중인 모든 비교과 프로그램 불러오기
        List<Program> currentPrograms = programRepository.findAll();

        // 프롬프트 생성 (GptPromptBuilder는 멤버와 프로그램 리스트를 받아서 String 반환한다고 가정)
        String prompt = gptPromptBuilder.createGptPrompt(member, currentPrograms);

        String gptResponse = gptClient.requestRecommendation(prompt);
        //로그
        //System.out.println("GPT response:");
        //System.out.println(gptResponse);
        // JSON 파싱
        ObjectMapper mapper = new ObjectMapper();
        String content = "";

        try {
            JsonNode rootNode = mapper.readTree(gptResponse);
            content = rootNode.path("choices").get(0).path("message").path("content").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 필요시 예외에 대한 처리 로직 또는 기본값 대입
            content = "";
        }
        //로그확인
        //System.out.println("GPT content:");
        //System.out.println(content);
        // GPT 응답에서 추천된 프로그램 ID들 추출
        List<Long> recommendedIds = Arrays.stream(content.split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .filter(s -> s.matches("\\d+"))
                .map(Long::parseLong)  // 문자열 → Long
                .collect(Collectors.toList());
        //로그확인
        //System.out.println("✅ 추천된 프로그램 ID들:");
        //recommendedIds.forEach(id -> System.out.println(" - ID: " + id));

        // 추천 프로그램 DB 저장용 리스트 생성
        for (Long id : recommendedIds) {
            currentPrograms.stream()
                    .filter(p -> p.getId().equals(id))  // ID 비교
                    .findFirst()
                    .ifPresent(program -> {
                        System.out.println("Saving recommendProgram for ID: " + program.getId());

                        RecommendProgram recommendProgram = new RecommendProgram();
                        recommendProgram.setMember(member);
                        recommendProgram.setProgram(program);
                        recommendProgram.setTitle(program.getTitle());

                        member.addRecommendProgram(recommendProgram);  // 헬퍼 메서드 사용
                    });
        }
        // 멤버 저장 (cascade 옵션으로 RecommendProgram도 같이 저장)
        memberRepository.save(member);
        List<RecommendProgramDto> dtoList = member.getRecommendPrograms().stream()
                .map(rp -> new RecommendProgramDto(rp.getId(), rp.getTitle(),rp.getProgram() != null ? rp.getProgram().getId() : null))
                .collect(Collectors.toList());
        return new GptRecommendationProgramRes(dtoList);
    }

    @Override
    public List<RoadmapProgramRes> RoadmapGet(Long memberId) {
        //멤버 id를 확인하여 멤버 존재 여부 확인
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        //추천 프로그램 Repository에서 memberId로 탐색
        List<RecommendProgram> recommendPrograms = recommendProgramRepository.findAllByMemberId(memberId);
        if (recommendPrograms.isEmpty()) {
            throw new RecommendNotFoundException();
        }
        //RoadmapRes형식으로 프로그램 정보를 저장.
        List<RoadmapProgramRes> result = recommendPrograms.stream()
                .map(rp -> {
                    Program p = rp.getProgram();
                    return new RoadmapProgramRes(
                            p.getId(),
                            p.getTitle(),
                            p.getGrade(),
                            p.getStart_date(),
                            p.getEnd_date(),
                            p.getPoint(),
                            p.getTags(),
                            List.of(
                                    p.getCreativity(),
                                    p.getIntegration(),
                                    p.getManagement(),
                                    p.getInterpersonal(),
                                    p.getGlobalCommunication(),
                                    p.getGlobalCitizenship()
                            )
                    );
                })
                .collect(Collectors.toList());

        return  result;
    }

    @Override
    public RecommendProgramRes RecommendProgramDetails(Long programId) {
        //프로그램 존재 여부 확인
        Program program = programRepository.findById(programId)
                .orElseThrow(ListProgramNotFoundException::new);

        return new RecommendProgramRes(
                program.getId(),
                program.getThumbnail_url(),
                program.getTitle(),
                program.getLink_url(),
                program.getStart_date(),
                program.getEnd_date(),
                program.getStatus(),
                program.getPoint(),
                program.getTags()
        );
    }

    @Override
    public ProgramDetailRes PopularProgramDetails(Long memberId, Long programId) {
        //프로그램 존재 여부 확인
        Program program = programRepository.findById(programId)
                .orElseThrow(ListProgramNotFoundException::new);

         boolean isBookmarked = bookmarkRepository.existsByMemberIdAndProgramId(memberId, program.getId());
         boolean hasNotification = notificationRepository.existsByMemberIdAndProgramId(memberId, program.getId());


        return new ProgramDetailRes(
                program.getId(),
                program.getThumbnail_url(),
                program.getTitle(),
                program.getLink_url(),
                program.getStart_date(),
                program.getEnd_date(),
                program.getPoint(),
                program.getStatus(),
                isBookmarked,
                hasNotification,
                program.getTags()
        );
    }
}

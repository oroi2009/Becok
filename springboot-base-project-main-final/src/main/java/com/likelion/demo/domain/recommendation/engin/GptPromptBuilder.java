package com.likelion.demo.domain.recommendation.engin;

import com.likelion.demo.domain.member.entity.Member;
import com.likelion.demo.domain.member.entity.RecommendType;
import com.likelion.demo.domain.participation.entity.ProgramRecord;
import com.likelion.demo.domain.programData.entity.Program;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GptPromptBuilder {
    public String createGptPrompt(Member member, List<Program> currentPrograms, List<String> bestPrograms) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("당신은 대학생 비교과 프로그램 추천 전문가입니다.\n");
        prompt.append("다음 학생 정보를 기반으로, 아래 제공된 비교과 프로그램 중에서 최대한 많이 추천해 주세요.\n");
        prompt.append("추천 기준은 학생의 목표, 학년/학기, 비교과 포인트, 관심 분야, 비교과 참여 경험(추천 타입)입니다.\n");
        prompt.append("참고로 '비교과 로드맵 우수 사례'도 함께 제공됩니다.\n\n");

        // [1] 학생 정보 작성
        prompt.append("### 학생 정보\n");
        prompt.append("- 목표: ").append(member.getGoal()).append("\n");
        prompt.append("- 학년/학기: ").append(member.getGrade()).append("학년 ").append(member.getSemester()).append("학기\n");
        prompt.append("- 비교과 포인트: ").append(member.getProgramPoint()).append("점\n");

        // 관심 분야 추출
        List<String> interests = member.getMemberInterestList().stream()
                .map(mi -> mi.getInterest().getInterestType().name()) // enum → 문자열
                .collect(Collectors.toList());
        prompt.append("- 관심 분야: ").append(interests.isEmpty() ? "없음" : interests).append("\n");

        // 추천 타입 설명
        prompt.append("- 추천 타입: ").append(member.getRecommendType()).append(" (")
                .append(getRecTypeExplanation(member.getRecommendType())).append(")\n\n");

        //참여 프로그램 리스트 추출
        List<String> participatedProgramTitles = member.getPrograms().stream()
                .map(ProgramRecord::getTitle)
                .collect(Collectors.toList());
        prompt.append("\n### 해당 학생이 이전에 참여한 비교과 프로그램 목록\n");
        for (String title : participatedProgramTitles) {
            prompt.append("- ").append(title).append("\n");
        }

        // [2] 우수 프로그램
        prompt.append("### 비교과 로드맵 우수 사례 목록 (참고용)\n");
        for (String title : bestPrograms) {
            prompt.append("- ").append(title).append("\n");
        }

        // [3] 현재 비교과 프로그램
        prompt.append("\n### 현재 진행 중인 비교과 프로그램 목록\n");

        for (Program program : currentPrograms) {
            String formattedProgram = String.format(
                    "제목: %s\n창의력: %d, 융합능력: %d, 자기관리능력: %d, 대인관계능력: %d, 글로벌소통능력: %d, 글로벌시민의식: %d\n" +
                            "대상: %s, 학년: %s, 학과: %s, 비교과포인트: %d, 조회수: %d\n\n",
                    program.getTitle(),
                    program.getCreativity(),
                    program.getIntegration(),
                    program.getManagement(),
                    program.getInterpersonal(),
                    program.getGlobalCommunication(),
                    program.getGlobalCitizenship(),
                    program.getTarget(),
                    program.getGrade(),
                    program.getDepartment(),
                    program.getPoint(),
                    program.getHit()
            );
            prompt.append(formattedProgram);
        }

        // [4] 요청 사항
        prompt.append("\n### 요청\n");
        prompt.append("학생에게 어울릴만한 비교과 프로그램을 최대한 많이 추천해 주세요.\n");
        prompt.append("형식은 아래와 같이 제목만 나열해주세요.\n");
        prompt.append("1. 프로그램 제목\n2. 프로그램 제목\n...");

        return prompt.toString();
    }


    private String getRecTypeExplanation(RecommendType type) {
        if (type == null) return "정보 없음";

        switch (type) {
            case EXPERIENCED:
                return "상대적으로 부족한 역량의 프로그램 추천이 필요한 학생";
            case LACKING:
                return "주로 참여한 역량의 프로그램 추천이 필요한 학생";
            case FIRST:
                return "비교과 프로그램에 처음 참여하는 학생";
            default:
                return "정보 없음";
        }
    }
}

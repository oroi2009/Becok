package com.likelion.demo.domain.recommendation.web.controller;

import com.likelion.demo.domain.member.web.dto.CreateGoalReq;
import com.likelion.demo.domain.member.web.dto.CreateGoalRes;
import com.likelion.demo.domain.programData.web.dto.ProgramDto;
import com.likelion.demo.domain.programData.web.dto.RoadmapProgramRes;
import com.likelion.demo.domain.recommendation.service.GptRecommendationService;
import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;
import com.likelion.demo.domain.recommendation.web.dto.RecommendProgramRes;
import com.likelion.demo.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {
    private final GptRecommendationService gptRecommendationService;

    //GPT 추천 요청
    @PostMapping("/recommend/{memberId}")
    public ResponseEntity<SuccessResponse<?>> RecommendProgram( @PathVariable Long memberId) {
        GptRecommendationProgramRes res = gptRecommendationService.RecommendProgram(memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(res));
    }

    //추천 로드맵 가져오기
    @GetMapping("/recommend/roadmap/{memberId}")
    public ResponseEntity<SuccessResponse<?>> RoadmapGet( @PathVariable Long memberId) {
        List<RoadmapProgramRes> res = gptRecommendationService.RoadmapGet(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(res));
    }

    //추천 비교과 상세 조회
    @GetMapping("/recommend/list/program/{programId}")
    public ResponseEntity<SuccessResponse<?>> RecommendProgramDetails( @PathVariable Long programId) {
        RecommendProgramRes res = gptRecommendationService.RecommendProgramDetails(programId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(res));
    }

    //인기 비교과 상세 조회
    @GetMapping("/popular/programs/{programId}")
    public ResponseEntity<SuccessResponse<?>> PopularProgramDetails( @PathVariable Long programId) {
        RecommendProgramRes res = gptRecommendationService.PopularProgramDetails(programId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(res));
    }
}

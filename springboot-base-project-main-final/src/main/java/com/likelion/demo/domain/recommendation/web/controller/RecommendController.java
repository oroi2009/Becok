package com.likelion.demo.domain.recommendation.web.controller;

import com.likelion.demo.domain.programData.web.dto.RoadmapProgramRes;
import com.likelion.demo.domain.recommendation.service.GptRecommendationService;
import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;
import com.likelion.demo.domain.recommendation.web.dto.ProgramDetailRes;
import com.likelion.demo.domain.recommendation.web.dto.RecommendContestRes;
import com.likelion.demo.domain.recommendation.web.dto.RecommendProgramRes;
import com.likelion.demo.global.response.SuccessResponse;
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
        // 추천 공모전 서비스 로직 포함
        gptRecommendationService.RecommendContest(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(res));
    }

    //추천 로드맵 가져오기
    @GetMapping("/recommend/roadmap/{memberId}")
    public ResponseEntity<SuccessResponse<?>> RoadmapGet( @PathVariable Long memberId) {
        List<RoadmapProgramRes> res = gptRecommendationService.RoadmapGet(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(res));
    }

    //추천 공모전 리스트 조회
    @GetMapping("/recommend/list/{memberId}")
    public ResponseEntity<SuccessResponse<?>> getRecommendContestList(@PathVariable Long memberId) {
        List<RecommendContestRes> res = gptRecommendationService.getRecommendContest(memberId);
        return ResponseEntity.ok(SuccessResponse.ok(res));
    }

    //추천 비교과 상세 조회 --- 사용 x
    @GetMapping("/recommend/list/program/{programId}")
    public ResponseEntity<SuccessResponse<?>> RecommendProgramDetails( @PathVariable Long programId) {
        RecommendProgramRes res = gptRecommendationService.RecommendProgramDetails(programId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(res));
    }

    //비교과 상세 조회
    @GetMapping("/programs/{programId}")
    public ResponseEntity<SuccessResponse<?>> PopularProgramDetails( @RequestParam Long memberId,@PathVariable Long programId) {
        ProgramDetailRes res = gptRecommendationService.PopularProgramDetails(memberId, programId);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(res));
    }
}

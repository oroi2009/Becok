package com.likelion.demo.domain.recommendation.web.controller;

import com.likelion.demo.domain.member.web.dto.CreateGoalReq;
import com.likelion.demo.domain.member.web.dto.CreateGoalRes;
import com.likelion.demo.domain.recommendation.service.GptRecommendationService;
import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;
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
public class RecommendController {
    private final GptRecommendationService gptRecommendationService;

    //GPT 추천 요청
    @PostMapping("/recommend/{memberId}")
    public ResponseEntity<SuccessResponse<?>> RecommendProgram( @PathVariable Long memberId) {
        GptRecommendationProgramRes res = gptRecommendationService.RecommendProgram(memberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(res));
    }
}

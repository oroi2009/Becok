package com.likelion.demo.domain.recommendation.service;


import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;

public interface GptRecommendationService {

    GptRecommendationProgramRes RecommendProgram(Long memberId);
}

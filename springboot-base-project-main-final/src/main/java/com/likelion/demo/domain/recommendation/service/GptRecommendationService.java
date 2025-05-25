package com.likelion.demo.domain.recommendation.service;


import com.likelion.demo.domain.programData.web.dto.ProgramDto;
import com.likelion.demo.domain.programData.web.dto.RoadmapProgramRes;
import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;
import com.likelion.demo.domain.recommendation.web.dto.RecommendProgramRes;

import java.util.List;

public interface GptRecommendationService {
    //gpt 추천
    GptRecommendationProgramRes RecommendProgram(Long memberId);
    //추천 비교과 list 가져오기
    List<RoadmapProgramRes> RoadmapGet(Long memberId);
    //추천 비교과 상세 정보 가져오기
    RecommendProgramRes RecommendProgramDetails(Long programId);
}

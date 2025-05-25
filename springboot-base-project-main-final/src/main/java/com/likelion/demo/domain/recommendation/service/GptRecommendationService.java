package com.likelion.demo.domain.recommendation.service;


import com.likelion.demo.domain.programData.web.dto.ProgramDto;
import com.likelion.demo.domain.programData.web.dto.RoadmapProgramRes;
import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;
import java.util.List;

public interface GptRecommendationService {

    GptRecommendationProgramRes RecommendProgram(Long memberId);
    List<RoadmapProgramRes> RoadmapGet(Long memberId);
}

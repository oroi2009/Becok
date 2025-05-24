package com.likelion.demo.domain.recommendation.service;

import com.likelion.demo.domain.recommendation.repository.RecommendProgram;
import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptRecommendationServiceImpl implements GptRecommendationService {
    private final RecommendProgram recommendProgram;


    @Override
    public GptRecommendationProgramRes RecommendProgram(Long memberId) {

        //res는 비교과프로그램 id와 제목으로 매핑 되서 나가야 함.
        return null;
    }
}

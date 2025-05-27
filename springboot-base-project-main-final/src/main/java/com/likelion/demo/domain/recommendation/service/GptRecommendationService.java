package com.likelion.demo.domain.recommendation.service;

import com.likelion.demo.domain.programData.web.dto.RoadmapProgramRes;
import com.likelion.demo.domain.recommendation.web.dto.GptRecommendationProgramRes;
import com.likelion.demo.domain.recommendation.web.dto.ProgramDetailRes;
import com.likelion.demo.domain.recommendation.web.dto.RecommendContestRes;
import com.likelion.demo.domain.recommendation.web.dto.RecommendProgramRes;
import java.util.List;

public interface GptRecommendationService {
    //
    // gpt 추천 공모전 db에 삽입
    void RecommendContest(Long memberId);

    List<RecommendContestRes> getRecommendContest(Long memberId);

    //gpt 추천
    GptRecommendationProgramRes RecommendProgram(Long memberId);
    //추천 비교과 list 가져오기
    List<RoadmapProgramRes> RoadmapGet(Long memberId);
    //추천 비교과 상세 정보 가져오기
    RecommendProgramRes RecommendProgramDetails(Long programId);
    //인기 비교과 상세 정보 가져오기
    ProgramDetailRes PopularProgramDetails(Long memberId, Long programId);

}

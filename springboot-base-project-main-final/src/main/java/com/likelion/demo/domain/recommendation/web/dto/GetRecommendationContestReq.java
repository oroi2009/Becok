package com.likelion.demo.domain.recommendation.web.dto;

import com.likelion.demo.domain.contest.entity.Contest;

public record GetRecommendationContestReq( // 요청 토큰 수를 줄이기 위해 필요한 정보만 삽입
                                           Long id,
                                           String category,
                                           String name
) {
    public static GetRecommendationContestReq from(Contest contest) {
        return new GetRecommendationContestReq(contest.getId(), contest.getCategory(), contest.getName());
    }
}

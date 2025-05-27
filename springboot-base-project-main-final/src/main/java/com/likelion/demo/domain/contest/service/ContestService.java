package com.likelion.demo.domain.contest.service;
import com.likelion.demo.domain.contest.web.dto.ContestDetailRes;
import com.likelion.demo.domain.contest.web.dto.ContestPopularRes;

import java.util.List;

public interface ContestService {
    List<ContestPopularRes> getPopularContests(); //인기 공모전 조회
    ContestDetailRes getContestDetail(Long memberId, Long contestId);
    void syncContestsFromLinkareer(); // 크롤링 후 저장
}

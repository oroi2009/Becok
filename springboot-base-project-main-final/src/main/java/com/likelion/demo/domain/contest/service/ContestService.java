package com.likelion.demo.domain.contest.service;
import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.web.dto.ContestDetailRes;
import com.likelion.demo.domain.contest.web.dto.ContestRes;

import java.util.List;

public interface ContestService {
    List<ContestRes> getPopularContests();
    ContestDetailRes getContestDetail(Long contestId);
    void syncContestsFromLinkareer(); // 크롤링 후 저장
}

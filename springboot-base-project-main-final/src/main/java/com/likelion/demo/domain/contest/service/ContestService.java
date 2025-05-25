package com.likelion.demo.domain.contest.service;
import com.likelion.demo.domain.contest.web.dto.ContestRes;

import java.util.List;

public interface ContestService {
    List<ContestRes> getPopularContests();

    void syncContestsFromLinkareer(); // 크롤링 후 저장
}

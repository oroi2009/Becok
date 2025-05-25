package com.likelion.demo.domain.contest.service;

import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.exception.ContestNotFoundException;
import com.likelion.demo.domain.contest.repository.ContestRepository;
import com.likelion.demo.domain.contest.web.dto.ContestRes;
import com.likelion.demo.global.crawler.LinkareerCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;
    private final LinkareerCrawler linkareerCrawler;

    // 공모전 인기 상위 5개 카드 반환
    @Override
    public List<ContestRes> getPopularContests() {
        List<Contest> contests = contestRepository.findTop5ByOrderByHitsDesc();

        if (contests.isEmpty()) {
            throw new ContestNotFoundException();
        }

        return contests.stream()
                .map(ContestRes::from)
                .collect(Collectors.toList());
    }

    //링커리어 데이터 연동
    @Override
    public void syncContestsFromLinkareer() {
        List<Contest> crawledContests = linkareerCrawler.crawl();
        contestRepository.saveAll(crawledContests);
    }
}

package com.likelion.demo.domain.contest.service;

import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.exception.ContestNotFoundException;
import com.likelion.demo.domain.contest.repository.ContestRepository;
import com.likelion.demo.domain.contest.web.dto.ContestDetailRes;
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
                .map(contest -> {
                    // TODO: 북마크/알림 여부를 반환 로직 필요
//                    boolean isBookmarked = bookmarkRepository.existsByMemberIdAndContestId(memberId, contest.getId());
//                    boolean hasNotification = notificationRepository.existsByMemberIdAndContestId(memberId, contest.getId());
                    boolean isBookmarked = true;
                    boolean hasNotification = true;

                    return ContestRes.from(contest, isBookmarked, hasNotification);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ContestDetailRes getContestDetail(Long contestId) {
        Contest contest = contestRepository.findById(contestId).orElseThrow(ContestNotFoundException::new);
        // TODO: 북마크/알림 여부를 반환 로직 필요
//                    boolean isBookmarked = bookmarkRepository.existsByMemberIdAndContestId(memberId, contest.getId());
//                    boolean hasNotification = notificationRepository.existsByMemberIdAndContestId(memberId, contest.getId());

        boolean isBookmarked = true;
        boolean hasNotification = true;
        return ContestDetailRes.from(contest, isBookmarked, hasNotification);
    }

    //링커리어 데이터 연동
    @Override
    public void syncContestsFromLinkareer() {
        List<Contest> crawledContests = linkareerCrawler.crawl();
        contestRepository.saveAll(crawledContests);
    }
}

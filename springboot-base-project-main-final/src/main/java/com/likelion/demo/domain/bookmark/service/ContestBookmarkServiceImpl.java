package com.likelion.demo.domain.bookmark.service;

import com.likelion.demo.domain.bookmark.entity.ContestBookmark;
import com.likelion.demo.domain.bookmark.repository.ContestBookmarkRepository;
import com.likelion.demo.domain.contest.exception.ContestNotFoundException;
import com.likelion.demo.domain.contest.repository.ContestRepository;
import com.likelion.demo.domain.member.exception.MemberNotFoundException;
import com.likelion.demo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContestBookmarkServiceImpl implements ContestBookmarkService {
    private final ContestBookmarkRepository contestBookmarkRepository;
    private final MemberRepository memberRepository;
    private final ContestRepository contestRepository;

    @Override
    public boolean toggle(Long memberId, Long contestId) {
        Optional<ContestBookmark> foundBookmark =
                contestBookmarkRepository.findByMember_IdAndContest_Id(memberId, contestId);

        // 북마크가 눌려있는 경우
        if (foundBookmark.isPresent()) {
            contestBookmarkRepository.delete(foundBookmark.get());
            return false; // 북마크 OFF
        }

        // 북마크가 안 눌려있는 경우
        var member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new); // 404 : 회원 정보 없음

        var contest = contestRepository.findById(contestId)
                .orElseThrow(ContestNotFoundException::new); // 404 : 공모전 정보 없음

        ContestBookmark bookmark = ContestBookmark.builder()
                .member(member)
                .contest(contest)
                .build();

        contestBookmarkRepository.save(bookmark);
        return true; // 북마크 ON
    }
}

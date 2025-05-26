package com.likelion.demo.domain.bookmark.service;

import com.likelion.demo.domain.bookmark.entity.ContestBookmark;
import com.likelion.demo.domain.bookmark.repository.ContestBookmarkRepository;
import com.likelion.demo.domain.contest.repository.ContestRepository;
import com.likelion.demo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContestBookmarkImpl implements ContestBookmarkService {
    private final ContestBookmarkRepository repository;
    private final MemberRepository memberRepository;
    private final ContestRepository contestRepository;

    @Override
    public boolean toggle(Long contestId, Long memberId) {
        boolean exists = repository.existsByMember_IdAndProgram_Id(memberId, contestId);
        if (exists) {
            repository.deleteByMember_IdAndProgram_Id(memberId, contestId);
            return true;
        }
        ContestBookmark bookmark = ContestBookmark.builder()
                .member(memberRepository.getReferenceById(memberId))
                .contest(contestRepository.getReferenceById(contestId))
                .build();
        repository.save(bookmark);
        return true;
    }
}

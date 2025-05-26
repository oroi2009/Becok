package com.likelion.demo.domain.bookmark.repository;

import com.likelion.demo.domain.bookmark.entity.ContestBookmark;
import com.likelion.demo.domain.contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestBookmarkRepository extends JpaRepository<ContestBookmark, Long> {
    boolean existsByMember_IdAndProgram_Id(Long memberId, Long programId);
    void deleteByMember_IdAndProgram_Id(Long memberId, Long programId);
}

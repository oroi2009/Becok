package com.likelion.demo.domain.bookmark.repository;

import com.likelion.demo.domain.bookmark.entity.ContestBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContestBookmarkRepository extends JpaRepository<ContestBookmark, Long> {
    List<ContestBookmark> findByMember_Id(Long memberId);
    Optional<ContestBookmark> findByMember_IdAndContest_Id(Long memberId, Long contestId);
    boolean existsByMemberIdAndContestId(Long memberId, Long contestId);
    void deleteByMember_IdAndContest_Id(Long memberId, Long contestId);
}

package com.likelion.demo.domain.bookmark.repository;

import com.likelion.demo.domain.bookmark.entity.ProgramBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramBookmarkRepository extends JpaRepository<ProgramBookmark, Long> {
    List<ProgramBookmark> findByMember_Id(Long memberId);
    Optional<ProgramBookmark> findByMember_IdAndProgram_Id(Long memberId, Long programId);
    void deleteByMember_IdAndProgram_Id(Long memberId, Long programId);
}

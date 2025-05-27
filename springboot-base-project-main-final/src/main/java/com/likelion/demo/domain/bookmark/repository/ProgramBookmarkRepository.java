package com.likelion.demo.domain.bookmark.repository;

import com.likelion.demo.domain.bookmark.entity.ProgramBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramBookmarkRepository extends JpaRepository<ProgramBookmark, Long> {
    Optional<ProgramBookmark> findByMember_IdAndProgram_Id(Long memberId, Long programId);
}

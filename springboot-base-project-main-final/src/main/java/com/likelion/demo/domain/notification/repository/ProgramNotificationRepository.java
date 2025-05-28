package com.likelion.demo.domain.notification.repository;

import com.likelion.demo.domain.bookmark.entity.ProgramBookmark;
import com.likelion.demo.domain.notification.entity.ProgramNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramNotificationRepository extends JpaRepository<ProgramNotification, Long> {
    Optional<ProgramNotification> findByMember_IdAndProgram_Id(Long memberId, Long programId);
    List<ProgramNotification> findByMember_Id(Long memberId);

    boolean existsByMemberIdAndProgramId(Long memberId, Long programId);

    @Query("SELECT pn FROM ProgramNotification pn JOIN FETCH pn.program p WHERE p.end_date = :targetDate")
    List<ProgramNotification> findAllWithProgramByEndDate(@Param("targetDate") LocalDate targetDate);
}

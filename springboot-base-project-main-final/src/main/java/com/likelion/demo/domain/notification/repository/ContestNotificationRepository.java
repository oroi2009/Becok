package com.likelion.demo.domain.notification.repository;

import com.likelion.demo.domain.notification.entity.ContestNotification;
import com.likelion.demo.domain.notification.entity.ProgramNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContestNotificationRepository extends JpaRepository<ContestNotification, Long> {
    Optional<ContestNotification> findByMember_IdAndContest_Id(Long memberId, Long contestId);
    List<ContestNotification> findByMember_Id(Long memberId);

    @Query("SELECT cn FROM ContestNotification cn JOIN FETCH cn.contest c WHERE c.endDate = :targetDate")
    List<ContestNotification> findAllWithContestByEndDate(@Param("targetDate") LocalDate targetDate);


    boolean existsByMemberIdAndContestId(Long memberId, Long contestId);
}

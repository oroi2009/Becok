package com.likelion.demo.domain.notification.repository;

import com.likelion.demo.domain.notification.entity.ContestNotification;
import com.likelion.demo.domain.notification.entity.ProgramNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContestNotificationRepository extends JpaRepository<ContestNotification, Long> {
    Optional<ContestNotification> findByMember_IdAndContest_Id(Long memberId, Long contestId);
    List<ContestNotification> findByMember_Id(Long memberId);

    boolean existsByMemberIdAndContestId(Long memberId, Long contestId);
}

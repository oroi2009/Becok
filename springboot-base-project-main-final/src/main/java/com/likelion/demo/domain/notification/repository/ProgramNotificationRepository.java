package com.likelion.demo.domain.notification.repository;

import com.likelion.demo.domain.bookmark.entity.ProgramBookmark;
import com.likelion.demo.domain.notification.entity.ProgramNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramNotificationRepository extends JpaRepository<ProgramNotification, Long> {
    Optional<ProgramNotification> findByMember_IdAndProgram_Id(Long memberId, Long programId);
}

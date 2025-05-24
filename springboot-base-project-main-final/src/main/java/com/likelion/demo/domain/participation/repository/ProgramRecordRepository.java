package com.likelion.demo.domain.participation.repository;

import com.likelion.demo.domain.participation.entity.ProgramRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRecordRepository extends JpaRepository<ProgramRecord, Long> {
    List<ProgramRecord> findByMemberId(Long memberId);
}

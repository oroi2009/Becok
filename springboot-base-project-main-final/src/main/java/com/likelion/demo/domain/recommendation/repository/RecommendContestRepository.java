package com.likelion.demo.domain.recommendation.repository;

import com.likelion.demo.domain.recommendation.entity.RecommendContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendContestRepository extends JpaRepository<RecommendContest, Long> {
    List<RecommendContest> findAllByMemberId(Long memberId);
}

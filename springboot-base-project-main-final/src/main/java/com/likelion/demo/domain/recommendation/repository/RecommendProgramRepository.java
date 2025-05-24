package com.likelion.demo.domain.recommendation.repository;

import com.likelion.demo.domain.recommendation.entity.RecommendProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendProgramRepository extends JpaRepository<RecommendProgram, Long> {

}

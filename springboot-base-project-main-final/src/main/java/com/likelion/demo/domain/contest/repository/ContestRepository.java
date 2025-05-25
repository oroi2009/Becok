package com.likelion.demo.domain.contest.repository;

import com.likelion.demo.domain.contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> findTop5ByOrderByHitsDesc();
}


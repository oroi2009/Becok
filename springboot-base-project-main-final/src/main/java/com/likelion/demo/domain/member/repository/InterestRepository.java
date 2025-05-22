package com.likelion.demo.domain.member.repository;

import com.likelion.demo.domain.member.entity.Interest;
import com.likelion.demo.domain.member.entity.InterestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByInterestType(InterestType interestType);
}

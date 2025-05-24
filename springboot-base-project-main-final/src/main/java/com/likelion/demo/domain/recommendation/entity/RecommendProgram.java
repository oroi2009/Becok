package com.likelion.demo.domain.recommendation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.likelion.demo.domain.member.entity.Member;
import com.likelion.demo.domain.programData.entity.Program;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // 추천 받은 멤버 (Many RecommendPrograms to One Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    // 추천된 비교과 프로그램 (Many RecommendPrograms to One Program)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
}

package com.likelion.demo.domain.member.entity;

import com.likelion.demo.domain.participation.entity.ProgramRecord;
import com.likelion.demo.domain.recommendation.entity.RecommendProgram;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String goal;

    private int grade;

    private int semester;

    private int programPoint;
    //관심 분야
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberInterest> memberInterestList = new ArrayList<>();
    //비교과 프로그램 참여 내역
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramRecord> programs = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendProgram> recommendPrograms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RecommendType recommendType;

    //연관 관계 편의 메서드
    public void addProgramRecord(ProgramRecord programRecord) {
        programs.add(programRecord);
        programRecord.setMember(this);
    }
}

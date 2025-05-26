package com.likelion.demo.domain.bookmark.entity;

import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.member.entity.Member;
import com.likelion.demo.domain.programData.entity.Program;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class ProgramBookmark {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;
}

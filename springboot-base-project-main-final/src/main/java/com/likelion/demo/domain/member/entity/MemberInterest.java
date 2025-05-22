package com.likelion.demo.domain.member.entity;

import com.likelion.demo.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInterest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    //연관 관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }
}

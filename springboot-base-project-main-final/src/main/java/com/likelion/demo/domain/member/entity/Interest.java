package com.likelion.demo.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InterestType interestType;

    @OneToMany(mappedBy = "interest", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MemberInterest> memberInterestList;

    //연관 관계 편의 메서드
    public void addMemberInterest(MemberInterest memberInterest) {
        memberInterestList.add(memberInterest);
        memberInterest.setInterest(this);
    }

}

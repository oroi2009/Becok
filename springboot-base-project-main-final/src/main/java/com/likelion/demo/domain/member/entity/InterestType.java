package com.likelion.demo.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterestType {
    MARKETING("마케팅"),
    SELF_DEVELOPMENT("자기개발"),
    VOLUNTEERING("자원봉사"),
    CAREER_ROADMAP("진로 로드맵"),
    STOCK_INVESTING("주식"),
    STARTUP("창업"),
    IT("IT"),
    CONTEST("공모전"),
    SELF_UNDERSTANDING("자기이해"),
    DESIGN("디자인"),
    TRAVEL("여행"),
    HUMANITIES_AND_ARTS("인문예술"),
    COUNSELING("상담"),
    BACKEND("백엔드"),
    SPORTS("스포츠"),
    WRITING("글쓰기"),
    CAREER_DECISION("진로의사 결정");

    private final String label;

    public String getLabel() {
        return label;
    }

}

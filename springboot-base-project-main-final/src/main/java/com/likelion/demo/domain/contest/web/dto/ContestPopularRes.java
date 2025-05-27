package com.likelion.demo.domain.contest.web.dto;

import com.likelion.demo.domain.contest.entity.Contest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ContestPopularRes {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private String organizer;
    private String category;
    private String Dday;
    private String imgUrl;
    private String detailUrl;
    private String hits;
    private String status;
    private boolean bookmarked; // 북마크 여부
    private boolean notification;    // 알림 여부

    public static ContestPopularRes from(Contest contest) {
        // status 계산
        LocalDate today = LocalDate.now();
        String status;

        if (today.isBefore(contest.getStartDate())) {
            status = "모집 중";
        } else if (!today.isAfter(contest.getEndDate())) {
            status = "모집 대기";
        } else {
            status = "모집 완료";
        }

        return ContestPopularRes.builder()
                .id(contest.getId())
                .name(contest.getName())
                .startDate(contest.getStartDate().toString())
                .endDate(contest.getEndDate().toString())
                .organizer(contest.getOrganizer())
                .category(contest.getCategory())
                .imgUrl(contest.getThumbnailUrl())
                .detailUrl(contest.getDetailUrl())
                .hits(String.valueOf(contest.getHits()))
                .Dday(contest.getDday())
                .status(contest.getStatus())
                .build();
    }
}

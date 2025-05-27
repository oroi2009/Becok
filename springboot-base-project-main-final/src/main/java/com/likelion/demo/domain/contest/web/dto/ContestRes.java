package com.likelion.demo.domain.contest.web.dto;

import com.likelion.demo.domain.contest.entity.Contest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ContestRes {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private String organizer;
    private String category;
    private String Dday;
    private String imgUrl;
    private String linkUrl;
    private String hits;
    private String status;
    private boolean bookmarked; // 북마크 여부
    private boolean notification;    // 알림 여부

    public static ContestRes from(Contest contest, boolean bookmarked, boolean notification) {
        // status 계산
        LocalDate today = LocalDate.now();
        String status;
        int dday;
        if (today.isBefore(contest.getStartDate())) {
            status = "모집예정";
        } else if (!today.isAfter(contest.getEndDate())) {
            status = "모집중";
        } else {
            status = "모집마감";
        }

        return ContestRes.builder()
                .id(contest.getId())
                .name(contest.getName())
                .startDate(contest.getStartDate().toString())
                .endDate(contest.getEndDate().toString())
                .organizer(contest.getOrganizer())
                .category(contest.getCategory())
                .imgUrl(contest.getThumbnailUrl())
                .linkUrl(contest.getLinkUrl())
                .hits(String.valueOf(contest.getHits()))
                .Dday(contest.getDday())
                .status(contest.getStatus())
                .bookmarked(bookmarked)
                .notification(notification)
                .build();
    }
}

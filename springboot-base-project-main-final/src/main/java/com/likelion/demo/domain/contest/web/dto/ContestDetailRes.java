package com.likelion.demo.domain.contest.web.dto;

import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.entity.enums.ContestStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ContestDetailRes {
    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private String thumbnail;
    private String detailUrl;
    private String Dday;
    private ContestStatus status;
    private String category;
    private String organizer;
    private boolean bookmarked; // 북마크 여부
    private boolean notification;    // 알림 여부

    public static ContestDetailRes from(Contest c, boolean bookmarked, boolean notification) {
        // status 계산
        LocalDate today = LocalDate.now();

        if (today.isBefore(c.getStartDate())) {
            c.setStatus(ContestStatus.UPCOMING); //시작 전
        } else if (!today.isAfter(c.getEndDate())) {
            c.setStatus(ContestStatus.ONGOING); //
        } else {
            c.setStatus(ContestStatus.CLOSED); //
        }

        return ContestDetailRes.builder()
                .id(c.getId())
                .name(c.getName())
                .thumbnail(c.getThumbnailUrl())
                .detailUrl(c.getDetailUrl())
                .startDate(String.valueOf(c.getStartDate()))
                .endDate(String.valueOf(c.getEndDate()))
                .Dday(c.getDday())
                .status(c.getStatus())
                .category(c.getCategory())
                .organizer(c.getOrganizer())
                .bookmarked(bookmarked)
                .notification(notification)
                .build();
    }
}

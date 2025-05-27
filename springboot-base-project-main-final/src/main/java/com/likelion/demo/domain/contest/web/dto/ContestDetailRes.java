package com.likelion.demo.domain.contest.web.dto;

import com.likelion.demo.domain.contest.entity.Contest;
import lombok.Builder;
import lombok.Getter;

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
    private String status;
    private String category;
    private String organizer;
    private boolean bookmarked; // 북마크 여부
    private boolean notification;    // 알림 여부

    public static ContestDetailRes from(Contest c, boolean bookmarked, boolean notification) {
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

package com.likelion.demo.domain.contest.web.dto;

import com.likelion.demo.domain.contest.entity.Contest;
import lombok.Builder;
import lombok.Getter;

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
    private String status;
    private String imgUrl;
    private String linkUrl;
    private String hits;

    public static ContestRes from(Contest contest) {
        return ContestRes.builder()
                .id(contest.getId())
                .name(contest.getName())
                .status(contest.getStatus())
                .startDate(contest.getStartDate().toString())
                .endDate(contest.getEndDate().toString())
                .organizer(contest.getOrganizer())
                .category(contest.getCategory())
                .imgUrl(contest.getThumbnailUrl())
                .linkUrl(contest.getLinkUrl())
                .hits(String.valueOf(contest.getHits()))
                .Dday(contest.getDday())
                .build();
    }
}

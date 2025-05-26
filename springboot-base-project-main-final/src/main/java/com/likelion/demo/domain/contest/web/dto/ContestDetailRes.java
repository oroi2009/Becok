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
    private String linkUrl;
    private String Dday;
    private String status;
    private String category;
    private String organizer;


    public static ContestDetailRes from(Contest c) {
        return ContestDetailRes.builder()
                .id(c.getId())
                .name(c.getName())
                .thumbnail(c.getThumbnailUrl())
                .linkUrl(c.getLinkUrl())
                .startDate(String.valueOf(c.getStartDate()))
                .endDate(String.valueOf(c.getEndDate()))
                .Dday(c.getDday())
                .status(c.getStatus().toString())
                .category(c.getCategory())
                .organizer(c.getOrganizer())
                .build();

    }
}

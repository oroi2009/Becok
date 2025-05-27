package com.likelion.demo.domain.recommendation.web.dto;

import com.likelion.demo.domain.contest.entity.Contest;
import com.likelion.demo.domain.contest.entity.enums.ContestStatus;
import com.likelion.demo.domain.programData.entity.ProgramStatus;
import com.likelion.demo.domain.recommendation.entity.RecommendContest;

import java.time.LocalDate;
import java.util.List;

public record RecommendContestRes(
        Long id,
        String title,
        String organizer,
        String thumbnailUrl,
        String linkUrl,
        LocalDate startDate,
        LocalDate endDate,
        ContestStatus status,
        List<String> category,
        Boolean bookmarked,
        Boolean notification
){
    public static RecommendContestRes of(Contest contest, ContestStatus status,
                                         List<String> category, Boolean bookmarked, Boolean notification) {
        return new RecommendContestRes(contest.getId(), contest.getName(),
                contest.getOrganizer(), contest.getThumbnailUrl(), contest.getLinkUrl(),
                contest.getStartDate(), contest.getEndDate(), status, category, bookmarked, notification);
    }
}
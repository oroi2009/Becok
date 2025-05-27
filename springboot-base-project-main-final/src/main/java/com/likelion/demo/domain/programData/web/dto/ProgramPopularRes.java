package com.likelion.demo.domain.programData.web.dto;

import com.likelion.demo.domain.bookmark.repository.ProgramBookmarkRepository;
import com.likelion.demo.domain.programData.entity.Program;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ProgramPopularRes {
    private Long id;
    private String thumbnailUrl;
    private String title;
    private String linkUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer point;
    private String status;        // UPCOMING, ONGOING, CLOSED
    private Boolean bookmarked;
    private Boolean notification;
    private List<String> category;
    private List<String> tags;
    private int hit;

    public static ProgramPopularRes from(Program p, Long memberId, boolean bookmarked, boolean notification) {
        // status 계산
        LocalDate today = LocalDate.now();
        String status;
        if (today.isBefore(p.getStart_date())) {
            status = "모집대기";
        } else if (!today.isAfter(p.getEnd_date())) {
            status = "모집중";
        } else {
            status = "모집마감";
        }

        return ProgramPopularRes.builder()
                .id(p.getId())
                .thumbnailUrl(p.getThumbnail_url())
                .title(p.getTitle())
                .linkUrl(p.getLink_url())
                .startDate(p.getStart_date())
                .endDate(p.getEnd_date())
                .point(p.getPoint())
                .status(status)
                .bookmarked(bookmarked)
                .notification(notification)
                .tags(p.getTags())
                .hit(p.getHit())
                .build();
    }
}

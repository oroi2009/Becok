package com.likelion.demo.domain.recommendation.web.dto;

import com.likelion.demo.domain.programData.entity.ProgramStatus;

import java.time.LocalDate;
import java.util.List;

public record ProgramDetailRes(
        Long id,
        String thumbnailUrl,
        String title,
        String linkUrl,
        LocalDate startDate,
        LocalDate endDate,
        int point,
        ProgramStatus status,
        boolean bookmarked, // 북마크 여부
        boolean notification,  // 알림 여부
        List<String> category
) {
}
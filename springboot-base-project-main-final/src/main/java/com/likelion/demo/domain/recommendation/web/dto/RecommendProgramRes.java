package com.likelion.demo.domain.recommendation.web.dto;

import com.likelion.demo.domain.programData.entity.ProgramStatus;

import java.time.LocalDate;
import java.util.List;

public record RecommendProgramRes (
        Long program_id,
        String thumbnailUrl,
        String title,
        String linkUrl,
        LocalDate startDate,
        LocalDate endDate,
        ProgramStatus status,
        int point,
        List<String> category
){
}
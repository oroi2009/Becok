package com.likelion.demo.domain.programData.web.dto;

import java.time.LocalDate;
import java.util.List;

public record RoadmapProgramRes (
        Long program_id,
        String title,
        String grade,
        LocalDate startDate,
        LocalDate endDate,
        int point,
        List<String> category,
        List<Integer> competencies
){
}

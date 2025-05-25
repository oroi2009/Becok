package com.likelion.demo.domain.recommendation.web.dto;

import com.likelion.demo.domain.recommendation.entity.RecommendProgram;

public record RecommendProgramDto(Long id, String title,Long programId) {
    public RecommendProgramDto(RecommendProgram recommendProgram) {
        this(recommendProgram.getId(), recommendProgram.getTitle(),recommendProgram.getProgram() != null ? recommendProgram.getProgram().getId() : null
        );
    }
}

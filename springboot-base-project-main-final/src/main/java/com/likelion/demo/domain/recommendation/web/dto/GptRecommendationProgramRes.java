package com.likelion.demo.domain.recommendation.web.dto;

import com.likelion.demo.domain.recommendation.entity.RecommendProgram;
import java.util.List;

public record GptRecommendationProgramRes(List<RecommendProgramDto> recommendPrograms) {

}

package com.likelion.demo.domain.recommendation.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecommendProgramErrorCode implements BaseResponseCode {
    //404 유저가 존재하지 않음
    RECOMMEND_PROGRAM_NOT_FOUND_404("RECOMMEND_PROGRAM_NOT_FOUND_404_1",404,"해당 유저의 추천 프로그램이 존재하지 않습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

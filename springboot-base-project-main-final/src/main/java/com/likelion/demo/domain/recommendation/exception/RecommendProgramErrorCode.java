package com.likelion.demo.domain.recommendation.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecommendProgramErrorCode implements BaseResponseCode {

    RECOMMEND_PROGRAM_NOT_FOUND_404("RECOMMEND_PROGRAM_NOT_FOUND_404_1",404,"해당 유저의 추천 프로그램이 존재하지 않습니다."),
    LIST_PROGRAM_NOT_FOUND_404("LIST_PROGRAM_NOT_FOUND_404_2",404,"해당 프로그램을 찾을 수 없습니다.");
    private final String code;
    private final int httpStatus;
    private final String message;
}

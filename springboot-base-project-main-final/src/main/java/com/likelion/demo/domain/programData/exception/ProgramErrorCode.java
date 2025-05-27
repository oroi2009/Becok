package com.likelion.demo.domain.programData.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgramErrorCode implements BaseResponseCode {
    //404 유저가 존재하지 않음
    PROGRAM_NOT_FOUND_404("PROGRAM_NOT_FOUND_404_1", 404, "해당 프로그램은 존재하지 않습니다."),
    NO_POPULAR_CONTENT_404("NO_POPULAR_CONTENT_404_1", 404, "표시할 인기 공모전이 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

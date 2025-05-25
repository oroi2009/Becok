package com.likelion.demo.domain.contest.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContestErrorCode implements BaseResponseCode {
    //404 유저가 존재하지 않음
    CONTEST_NOT_FOUND_404("CONTEST_NOT_FOUND_404_1",404,"해당 유저는 존재하지 않습니다."),
    NO_POPULAR_CONTEST("CONTEST_NO_POPULAR_400_1", 404, "이미 사용 중인 아이디입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}


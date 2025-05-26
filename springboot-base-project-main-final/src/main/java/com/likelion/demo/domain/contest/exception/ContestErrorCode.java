package com.likelion.demo.domain.contest.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContestErrorCode implements BaseResponseCode {
    //404 유저가 존재하지 않음
    POPULAR_CONTEST_NOT_FOUND_404("CONTEST_NOT_FOUND_404_1",404,"표시할 인기 공모전을 찾지 못했습니다."),
    NO_POPULAR_CONTEST("CONTEST_NO_POPULAR_404_2", 404, "표시할 인기 공모전이 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}


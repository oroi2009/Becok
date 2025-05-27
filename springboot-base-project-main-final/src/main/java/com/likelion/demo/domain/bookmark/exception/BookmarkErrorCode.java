package com.likelion.demo.domain.bookmark.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookmarkErrorCode implements BaseResponseCode {
    INVALID_BOOKMARK_TYPE("BOOKMARK_400_1", 400, "잘못된 북마크 타입입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
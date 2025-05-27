package com.likelion.demo.domain.remember.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RememberErrorCode implements BaseResponseCode {
    NO_REMEMBER_CONTENT_404("NO_REMEMBER_CONTENT_404", 404, "보관함에 표시할 항목이 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

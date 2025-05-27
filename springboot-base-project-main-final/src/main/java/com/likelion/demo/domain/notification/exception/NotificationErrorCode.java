package com.likelion.demo.domain.notification.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationErrorCode implements BaseResponseCode {
    INVALID_NOTIFICATION_TYPE("NOTIFICATION_400_1", 400, "잘못된 대외 활동 타입입니다.");


    private final String code;
    private final int httpStatus;
    private final String message;
}

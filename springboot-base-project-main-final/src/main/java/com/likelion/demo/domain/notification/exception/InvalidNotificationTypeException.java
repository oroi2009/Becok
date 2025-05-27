package com.likelion.demo.domain.notification.exception;

import com.likelion.demo.domain.bookmark.exception.BookmarkErrorCode;
import com.likelion.demo.global.exception.BaseException;

public class InvalidNotificationTypeException extends BaseException {
    public InvalidNotificationTypeException( ) {
        super(NotificationErrorCode.INVALID_NOTIFICATION_TYPE);
    }
}

package com.likelion.demo.domain.bookmark.exception;

import com.likelion.demo.global.exception.BaseException;

public class InvalidBookmarkTypeException extends BaseException {
    public InvalidBookmarkTypeException() {
        super(BookmarkErrorCode.INVALID_BOOKMARK_TYPE);
    }
}
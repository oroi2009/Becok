package com.likelion.demo.domain.programData.exception;

import com.likelion.demo.global.exception.BaseException;

public class NoPopularContentException extends BaseException {
    public NoPopularContentException() {
        super(ProgramErrorCode.NO_POPULAR_CONTENT_404);
    }
}

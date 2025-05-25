package com.likelion.demo.domain.programData.exception;

import com.likelion.demo.domain.member.exception.MemberErrorCode;
import com.likelion.demo.global.exception.BaseException;

public class ProgramNotFoundException extends BaseException {
    public ProgramNotFoundException() {
        super(ProgramErrorCode.PROGRAM_NOT_FOUND_404);
    }
}

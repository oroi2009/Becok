package com.likelion.demo.domain.member.exception;

import com.likelion.demo.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseResponseCode {
    //404 유저가 존재하지 않음
    MEMBER_NOT_FOUND_404("MEMBER_NOT_FOUND_404_1",404,"해당 유저는 존재하지 않습니다."),
    DUPLICATE_EMAIL("MEMBER_400_1", 400, "이미 사용 중인 아이디입니다."),
    PASSWORD_NOT_MATCH("MEMBER_400_2", 400, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");


    private final String code;
    private final int httpStatus;
    private final String message;
}

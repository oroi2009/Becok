package com.likelion.demo.domain.member.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRes {
    private Long id;
    private String email;
}

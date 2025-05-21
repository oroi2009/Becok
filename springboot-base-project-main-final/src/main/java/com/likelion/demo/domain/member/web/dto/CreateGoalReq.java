package com.likelion.demo.domain.member.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGoalReq {
    @NotBlank(message = "목표 내용은 비어있을 수 없습니다.")
    @Size(max = 100, message = "목표 내용은 100자까지 입력 가능합니다.")
    private String goal;
}

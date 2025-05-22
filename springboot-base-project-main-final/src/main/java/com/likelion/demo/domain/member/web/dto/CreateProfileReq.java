package com.likelion.demo.domain.member.web.dto;

import com.likelion.demo.domain.member.entity.InterestType;
import com.likelion.demo.domain.member.entity.MemberInterest;
import com.likelion.demo.domain.member.entity.RecommendType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CreateProfileReq {
    @Min(value = 1, message = "학년은 1 이상이어야 합니다.")
    @Max(value = 4, message = "학년은 4 이하여야 합니다.")
    private int grade;

    @Min(value = 1, message = "학기는 1 이상이어야 합니다.")
    @Max(value = 2, message = "학기는 2 이하여야 합니다.")
    private int semester;

    @Min(value = 0, message = "비교과 포인트는 0 이상이어야 합니다.")
    private int programPoint;

    @NotEmpty(message = "관심 분야는 비어있을 수 없습니다.")
    private List<InterestType> interests;

    @NotBlank(message = "비교과 내역은 비어 있을 수 없습니다.")
    private String joinedPrograms;

    @NotNull(message = "추천 타입은 비어있을 수 없습니다.")
    private RecommendType recommendType;
}

package com.likelion.demo.domain.programData.web.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProgramDto {
    private String title;
    private int point;
    //Json형식과 필드명 매핑
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("detail_url")
    private String detailUrl;
    @JsonProperty("recruit_period")
    private String recruitPeriod;
    private String target;
    @JsonProperty("grade_gender")
    private String gradeGender;
    private String department;
    private String tags;
    private List<Integer> competencies;
    private String description;
}

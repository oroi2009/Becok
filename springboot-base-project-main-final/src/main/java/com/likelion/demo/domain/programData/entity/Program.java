package com.likelion.demo.domain.programData.entity;

import com.likelion.demo.domain.recommendation.entity.RecommendProgram;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate start_date;

    private LocalDate end_date;
    //창의력
    private int creativity;
    //융합능력
    private int integration;
    //자기관리능력
    private int management;
    //대인관계능력
    private int interpersonal;
    //글로벌소통능력
    private int globalCommunication;
    //글로벌시민의식
    private int globalCitizenship;

    private String target;

    private String grade;

    private String department;

    private String thumbnail_url;

    private String Link_url;

    private int point;

    @ElementCollection
    private List<String> tags;

    private int hit;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendProgram> recommendPrograms = new ArrayList<>();

}

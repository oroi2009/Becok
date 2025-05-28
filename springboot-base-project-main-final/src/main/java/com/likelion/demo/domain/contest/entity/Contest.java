package com.likelion.demo.domain.contest.entity;

import com.likelion.demo.domain.contest.entity.enums.ContestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String organizer;
    private String category;

    @Enumerated(EnumType.STRING)
    private ContestStatus status;

    private String thumbnailUrl;
    private String detailUrl;
    private int hits;
    private String Dday;
}
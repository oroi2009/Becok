package com.likelion.demo.domain.programData.service;

import com.likelion.demo.domain.programData.web.dto.ProgramPopularRes;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface ProgramService {
    //비교과 프로그램 저장
    void importProgramsFromJson();

    //인기 비교과 프로그램 상위 5개 조회
    List<ProgramPopularRes> getPopularPrograms(Long memberId);
}

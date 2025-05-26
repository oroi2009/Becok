package com.likelion.demo.domain.programData.web.controller;

import com.likelion.demo.domain.member.web.dto.CreateGoalReq;
import com.likelion.demo.domain.member.web.dto.CreateGoalRes;
import com.likelion.demo.domain.programData.service.ProgramService;
import com.likelion.demo.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    //비교과 프로그램 저장
    public void importPrograms() {
        programService.importProgramsFromJson();
    }

}

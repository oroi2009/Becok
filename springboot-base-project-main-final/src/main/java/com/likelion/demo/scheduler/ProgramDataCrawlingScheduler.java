package com.likelion.demo.scheduler;

import com.likelion.demo.domain.contest.service.ContestService;
import com.likelion.demo.domain.programData.service.ProgramService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// TODO: 비교과 프로그램 크롤링 로직도 구현한 뒤
//       @Scheduled를 활용해 정기적으로 실행되도록 구성해야 함
@Component // 스프링이 자동으로 Bean으로 등록하여 스케줄링이 동작하게 함
@RequiredArgsConstructor // 생성자 주입을 자동으로 생성해주는 Lombok 어노테이션
public class ProgramDataCrawlingScheduler {
    private final ProgramService programService; // 공모전 크롤링 로직이 구현된 서비스 클래스

    // TODO: 현재는 앱 실행 시점에 1회만 크롤링이 수행됨
    //  → 이를 개선하여 매일 특정 시간에 자동으로 크롤링되도록 설정한 메서드
    // TODO: 추후 비교과 프로그램 크롤링 로직도 유사한 방식으로 스케줄링 적용할 수 있음
    @PostConstruct
    public void runAtStartup() {
        System.out.println("[서버 시작 시 실행] 비교과 프로그램 동기화 시작...");
        programService.importProgramsFromJson();
        System.out.println("[서버 시작 시 실행] 완료. DB 저장됨.");
        System.out.println("[서버 시작 시 실행] 비교과 프로그램 동기화 시작...");
        programService.importProgramsFromJson();
        System.out.println("[서버 시작 시 실행] 완료. DB 저장됨.");
//        System.out.println("[서버 시작 시 실행] 비교과 프로그램 동기화 시작...");
//        programService.importProgramsFromJson();
//        System.out.println("[서버 시작 시 실행] 완료. DB 저장됨.");
        System.out.println("[서버 시작 시 실행] 비교과 프로그램 동기화 시작...");
//        programService.importProgramsFromJson();
        System.out.println("[서버 시작 시 실행] 완료. DB 저장됨.");
    }
    // TODO: 일정 주기로 크롤링 되도록 설정 필요
    // 예시) 매일 오전 3시에 실행되도록 cron 설정
    // cron 표현식 설명: "초 분 시 일 월 요일"
    // 이 경우: 매일 03:00:00에 실행됨
    @Scheduled(cron = "0 0 6 * * *")
    public void syncProgram() {
        // 기존 DemoApplication.run()안에 있던 로직 이동
        System.out.println("[크롤링 스케줄러] 비교과 프로그램 동기화 시작...");
        programService.importProgramsFromJson(); //DB저장
        System.out.println("[크롤링 스케줄러] 완료. DB 저장됨.");
    }
}
package com.likelion.demo.scheduler;

import com.likelion.demo.domain.contest.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // 스프링이 자동으로 Bean으로 등록하여 스케줄링이 동작하게 함
@RequiredArgsConstructor // 생성자 주입을 자동으로 생성해주는 Lombok 어노테이션
public class ContestCrawlingScheduler {

    private final ContestService contestService; // 공모전 크롤링 로직이 구현된 서비스 클래스

    // TODO: 현재는 앱 실행 시점에 1회만 크롤링이 수행됨
    //  → 이를 개선하여 매일 특정 시간에 자동으로 크롤링되도록 설정한 메서드
    // TODO: 추후 비교과 프로그램 크롤링 로직도 유사한 방식으로 스케줄링 적용할 수 있음

    // TODO: 일정 주기로 크롤링 되도록 설정 필요
    // 예시) 매일 오전 3시에 실행되도록 cron 설정
    // cron 표현식 설명: "초 분 시 일 월 요일"
    // 이 경우: 매일 03:00:00에 실행됨
    @Scheduled(cron = "0 0 3 * * *")
    public void syncContestsFromLinkareer() {
        // 기존 DemoApplication.run()안에 있던 로직 이동
        System.out.println("[크롤링 스케줄러] 링커리어 공모전 동기화 시작...");
        contestService.syncContestsFromLinkareer();
        System.out.println("[크롤링 스케줄러] 완료. DB 저장됨.");
    }
}

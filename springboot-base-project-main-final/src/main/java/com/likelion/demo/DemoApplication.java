package com.likelion.demo;

import com.likelion.demo.domain.contest.service.ContestService;
import com.likelion.demo.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

    public DemoApplication(ContestService contestService) {
        this.contestService = contestService;
    }

    public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	private final ContestService contestService;

	//앱 실행 시 자동 실행되는 메서드
	@Override
	public void run(String... args) {
		// TODO: 현재는 크롤링 로직이 앱 실행 시 첫 1회만 동작하도록 구현되어 있음
		//  → @Scheduled를 사용해 주기적 실행되도록 리팩터링 필요해보임
		System.out.println("[크롤링 테스트] 링커리어 공모전 크롤링 시작...");
		contestService.syncContestsFromLinkareer(); // TODO: 해당 부분을 scheduler 패키지로 이동
		System.out.println("[크롤링 테스트] 완료. DB 저장됨.");
	}

	@Bean
	public CommandLineRunner dbConnectionCheck(
			@Value("${spring.datasource.url}") String dbUrl,
			@Value("${spring.datasource.username}") String dbUsername
	) {
		return args -> {
			System.out.println("=== Database Connection Info ===");
			System.out.println("URL: " + dbUrl);
			System.out.println("Username: " + dbUsername);
			System.out.println("================================");
		};
	}
}

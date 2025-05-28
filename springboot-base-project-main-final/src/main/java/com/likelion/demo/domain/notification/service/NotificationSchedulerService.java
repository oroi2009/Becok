package com.likelion.demo.domain.notification.service;

import com.likelion.demo.domain.notification.entity.ContestNotification;
import com.likelion.demo.domain.notification.entity.ProgramNotification;
import com.likelion.demo.domain.notification.repository.ContestNotificationRepository;
import com.likelion.demo.domain.notification.repository.ProgramNotificationRepository;
import com.likelion.demo.global.Mail.MailService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class NotificationSchedulerService {
    private final ProgramNotificationRepository programNotificationRepository;
    private final ContestNotificationRepository contestNotificationRepository;
    private final MailService mailService;


    //멤버 id 하나에 n개의 프로그램이 있다면 메일도 n개 나감.. 보완 필요 title,url <list>로 묶어서??
    @Transactional
    @Scheduled(cron = "0 0 9 * * ?") // 매일 오전 9시에 실행
    public void sendReminderEmails() {
        LocalDate today = LocalDate.now();

        // D-3 알림
        //비교과
        List<ProgramNotification> d3List =
                programNotificationRepository.findAllWithProgramByEndDate(today.plusDays(3));
        for (ProgramNotification noti : d3List) {
            mailService.sendEmail_3(
                    noti.getMember().getEmail(),
                    noti.getProgram().getTitle(),
                    noti.getProgram().getEnd_date(),
                    noti.getProgram().getLink_url()
            );
        }
        //공모전
        List<ContestNotification> d3List_contest =
                contestNotificationRepository.findAllWithContestByEndDate(today.plusDays(3));
        for (ContestNotification noti : d3List_contest) {
            mailService.sendEmail_3(
                    noti.getMember().getEmail(),
                    noti.getContest().getName(),
                    noti.getContest().getEndDate(),
                    noti.getContest().getDetailUrl() //link url 수정 필요
            );
        }

        // D-1 알림
        //비교과
        List<ProgramNotification> d1List =
                programNotificationRepository.findAllWithProgramByEndDate(today.plusDays(1));
        for (ProgramNotification noti : d1List) {
            mailService.sendEmail(
                    noti.getMember().getEmail(),
                    noti.getProgram().getTitle(),
                    noti.getProgram().getEnd_date(),
                    noti.getProgram().getLink_url()
            );
        }
        //공모전
        List<ContestNotification> d1List_contest =
                contestNotificationRepository.findAllWithContestByEndDate(today.plusDays(2));
        for (ContestNotification noti : d1List_contest) {
            mailService.sendEmail(
                    noti.getMember().getEmail(),
                    noti.getContest().getName(),
                    noti.getContest().getEndDate(),
                    noti.getContest().getDetailUrl() //link url 수정 필요
            );
        }
    }
}

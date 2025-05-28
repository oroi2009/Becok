package com.likelion.demo.global.Mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender mailSender; // 스프링에서 재공하는 이메일 발송기
    public void sendEmail_3(String email, String title, LocalDate enddate, String linkurl) {
        // 메일 메세지 구성
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email); // 수신자

        message.setSubject("\uD83D\uDCE2 [비콕 알림] 신청 마감 D-3! 지금 확인해보세요!"); // 제목
        message.setText("회원님이 알림 신청하신 프로그램의 마감이 얼마 남지 않았어요!\n\n" +
                "\uD83D\uDCD8 프로그램명:" + title +
                "\n\uD83D\uDDD3\uFE0F 접수 마감:" +enddate+
                "\n\uD83D\uDC49 [신청하러 가기]:"+linkurl+
                "\n놓치지 않도록 지금 확인해보세요!\n" +
                "- 비콕 드림 \uD83D\uDC8C"); // 내용

        // 메일 전송
        mailSender.send(message);
    }

    public void sendEmail(String email, String title, LocalDate enddate, String linkurl) {
        // 메일 메세지 구성
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email); // 수신자

        message.setSubject("\uD83D\uDCE2 [비콕 알림] 신청 마감 D-1! 지금 바로 신청하세요!"); // 제목
        message.setText("회원님이 알림 신청하신 프로그램의 마감이 하루 남았어요!\n\n" +
                "\uD83D\uDCD8 프로그램명:" + title +
                "\uD83D\uDDD3\uFE0F 접수 마감:" +enddate+
                "\n아직 신청하지 않으셨다면, 지금 바로 참여해보세요.  \n" +
                "마감 전까지 얼마 남지 않았습니다! ⏳\n" +
                "\uD83D\uDC49 [신청하러 가기]:"+linkurl+
                "- 비콕 드림 \uD83D\uDC8C"); // 내용

        // 메일 전송
        mailSender.send(message);
    }
}

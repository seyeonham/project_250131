package com.project_250131.user.bo;

import com.project_250131.config.MailConfig;
import com.project_250131.config.MailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    public int sendMail(String email) throws MessagingException {
        int passcode = ThreadLocalRandom.current().nextInt(111111, 1000000);

        String title = "맛집랭킹 비밀번호 찾기 인증번호";
        String from = mailProperties.getUsername();
        String to = email;

        String content =
                "안녕하세요. 맛집랭킹 비밀번호 찾기 인증 이메일 입니다.\n\n" +
                        "인증번호는 " + passcode + " 입니다. ";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(title);
        messageHelper.setText(content);

        mailSender.send(message);

        return passcode;
    }
}

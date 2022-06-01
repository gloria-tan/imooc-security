package com.billwen.learning.imooc.imoocsecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "mooc.email-provider", name = "name", havingValue = "smtp")
public class SmtpEmailService implements EmailService{

    private final JavaMailSender sender;

    @Override
    public void send(String email, String msg) {
        var message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("service@imooc.com");
        message.setSubject("慕课网实战Spring Security 登录验证码");
        message.setText("验证码为: " + msg);
        // sender.send(message);
        log.info("Send to {}, message {}", email, msg);
    }
}

package com.billwen.learning.imooc.imoocsecurity.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "mooc.email-provider", name = "name", havingValue = "sendgrid")
public class SendGridEmailService implements EmailService{

    private final SendGrid sendGrid;

    @Override
    public void send(String email, String msg) {
        var emailFrom = new Email("service@imooc.com");
        String subject = "慕课网实战Spring Security 登录验证码";
        var emailTo = new Email(email);
        var content = new Content("text/plain", "验证码为: " + msg);
        var mail = new Mail(emailFrom, subject, emailTo, content);
        var request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = this.sendGrid.api(request);
            if (response.getStatusCode() == 202) {
                log.info("邮件发送成功");
            } else {
                log.error(response.getBody());
            }
        } catch (IOException e) {
            log.error("请求发生异常 {}", e.getLocalizedMessage());
        }
    }
}

package com.billwen.learning.imooc.imoocsecurity.config;

import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "mooc.email-provider", name = "name", havingValue = "sendgrid")
public class SendGridConfig {

    private final AppProperties appProperties;

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(appProperties.getEmailProvider().getApiKey());
    }
}

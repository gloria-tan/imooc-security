package com.billwen.learning.imooc.uaa.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "mooc.sms-provider", name="name", havingValue = "ali")
public class AliConfig {

    private final AppProperties appProperties;

    @Bean
    public IAcsClient iAcsClient() {
        log.warn("Aliyun : dummy client");
        return null;
        // DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",appProperties.getAli().getApiKey(), appProperties.getAli().getApiSecret());
        // return new DefaultAcsClient(profile);
    }
}

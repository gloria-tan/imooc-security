package com.billwen.learning.imooc.imoocsecurity.config;

import cn.leancloud.core.AVOSCloud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "mooc.sms-provider", name = "name", havingValue = "leancloud")
public class LeanCloudConfig {

    private final AppProperties appProperties;

    @PostConstruct
    public void initialize() {
        // AVOSCloud.initialize(appProperties.getLeanClooud().getApiId(), appProperties.getLeanClooud().getApiKey());
        log.warn("LeanCloud: dummy");
    }
}

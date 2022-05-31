package com.billwen.learning.imooc.imoocsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mooc")
public class AppProperties {

    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        private Long accessTokenExpireTime = 60_000L;

        private Long refreshTokenExpireTime = 30 * 24 * 3600 * 1000L;
    }
}

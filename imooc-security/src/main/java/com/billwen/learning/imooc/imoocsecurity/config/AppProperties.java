package com.billwen.learning.imooc.imoocsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mooc")
public class AppProperties {

    private Jwt jwt = new Jwt();

    private Ali ali = new Ali();

    private LeanCloud leanClooud = new LeanCloud();

    private SmsProvider smsProvider = new SmsProvider();

    @Data
    public static class Jwt {
        private Long accessTokenExpireTime = 60_000L;

        private Long refreshTokenExpireTime = 30 * 24 * 3600 * 1000L;
    }

    @Data
    public static class Ali {
        private String apiKey;

        private String apiSecret;
    }

    @Data
    public static class LeanCloud {
        private String apiId;

        private String apiKey;
    }

    @Data
    public static class SmsProvider {
        private String name;

        private String apiUrl;
    }
}

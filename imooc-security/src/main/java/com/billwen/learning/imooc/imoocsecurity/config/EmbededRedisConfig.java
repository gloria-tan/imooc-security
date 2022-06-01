package com.billwen.learning.imooc.imoocsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Profile("!prod")
@Slf4j
@Configuration
public class EmbededRedisConfig {
    private RedisServer redisServer;

    public EmbededRedisConfig(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getPort());
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Starting embeded redis server");
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        log.info("Shutting down the embedded redis server");
        redisServer.stop();
    }
}

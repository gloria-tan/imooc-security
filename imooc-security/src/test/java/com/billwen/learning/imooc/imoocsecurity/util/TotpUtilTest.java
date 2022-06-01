package com.billwen.learning.imooc.imoocsecurity.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Key;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TotpUtilTest {

    private TotpUtil totpUtil;

    @BeforeEach
    private void setup() {
        this.totpUtil = new TotpUtil();
    }

    @Test
    public void givenSameKeyAndTotp_whenValidateTwice_thenFail() throws Exception {
        Instant now = Instant.now();
        Instant validFuture = now.plusSeconds(TotpUtil.TIME_STEP);
        Key key = totpUtil.generateKey();
        String firstTotp = this.totpUtil.createTotp(key, now);
        Key secondKey = this.totpUtil.generateKey();
        assertTrue(totpUtil.verifyTotp(key, firstTotp), "第一次验证成功");

        String secondTotp = this.totpUtil.createTotp(key, Instant.now());
        assertEquals(firstTotp, secondTotp, "时间间隔内生成的两个TOTP是一致的");

        String afterTimeStepTotp = totpUtil.createTotp(key, validFuture);
        assertNotEquals(firstTotp, afterTimeStepTotp, "过期之后和原来的TOTP比较应该不一致");
        assertFalse(totpUtil.verifyTotp(secondKey, firstTotp), "使用新的key验证原来的TOTP应该失败");
    }

    @Test
    public void givenKey_ThenEncodeAndDecodeSuccess() {
        Key key = this.totpUtil.generateKey();
        String stringifiedKey = this.totpUtil.encodeKeyToString(key);
        Key decodeKey = this.totpUtil.decodeKeyFromString(stringifiedKey);
        assertEquals(key, decodeKey, "从字符串中获得的 Key 解码后应该和原来的 key 一致。");
    }

    @Test
    public void generate_mfaKey() {
        String key = this.totpUtil.encodeKeyToString();
        log.debug("Key -- {}", key);
    }
}

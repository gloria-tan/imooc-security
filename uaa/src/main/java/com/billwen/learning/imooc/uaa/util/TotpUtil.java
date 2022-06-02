package com.billwen.learning.imooc.uaa.util;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class TotpUtil {

    public static final long TIME_STEP = 60 * 5L;

    private static final int PASSWORD_LENGTH = 6;

    private KeyGenerator keyGenerator;

    private TimeBasedOneTimePasswordGenerator totp;

    {
        try {
            this.totp = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(TIME_STEP), PASSWORD_LENGTH);
            this.keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
            // SHA-1 and SHA-256 需要64字节(512 位)的key
            // SHA 512 需要128字节（1024 位）的key
            this.keyGenerator.init(512);
        } catch (NoSuchAlgorithmException e) {
            log.error("没有找到算法 {}", e.getLocalizedMessage());

        }
    }

    /**
     * t
     * @param key
     * @param time
     * @return
     * @throws InvalidKeyException
     */
    public String createTotp(Key key, Instant time) throws InvalidKeyException {
        int password = totp.generateOneTimePassword(key, time);
        String format = "%0" + PASSWORD_LENGTH + "d";
        return String.format(format, password);
    }

    /**
     * 验证 TOTP key
     * @param key
     * @param code
     * @return
     * @throws InvalidKeyException
     */
    public boolean verifyTotp(Key key, String code) throws InvalidKeyException {
        var now = Instant.now();
        return code.equals(createTotp(key, now));
    }

    public Key generateKey() {
        return this.keyGenerator.generateKey();
    }

    public String encodeKeyToString(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String encodeKeyToString() {
        return encodeKeyToString(generateKey());
    }

    public Key decodeKeyFromString(String stringifiedKey) {
        return new SecretKeySpec(Base64.getDecoder().decode(stringifiedKey), this.totp.getAlgorithm());
    }

    public Optional<String> createTotp(String stringifiedKey) {
        try {
            return Optional.of(createTotp(decodeKeyFromString(stringifiedKey), Instant.now()));
        } catch (InvalidKeyException e) {
            return Optional.empty();
        }
    }
}

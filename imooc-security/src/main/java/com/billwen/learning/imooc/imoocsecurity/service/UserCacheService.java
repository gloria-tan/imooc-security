package com.billwen.learning.imooc.imoocsecurity.service;

import com.billwen.learning.imooc.imoocsecurity.config.Constants;
import com.billwen.learning.imooc.imoocsecurity.domain.User;
import com.billwen.learning.imooc.imoocsecurity.util.CryptoUtil;
import com.billwen.learning.imooc.imoocsecurity.util.TotpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCacheService {

    private final RedissonClient redisson;

    private final TotpUtil totpUtil;

    public String cacheUser(User user) {
        var mfaId = CryptoUtil.randomAlphanumeric(12);
        RMapCache<String, User> cache = redisson.getMapCache(Constants.CACHE_MFA);
        if ( !cache.containsKey(mfaId) ) {
            cache.put(mfaId, user, TotpUtil.TIME_STEP, TimeUnit.SECONDS);
        }

        return mfaId;
    }

    public Optional<User> retrieveUser(String mfaId) {
        RMapCache<String, User> cache = redisson.getMapCache(Constants.CACHE_MFA);
        if ( cache.containsKey(mfaId)) {
            return Optional.of(cache.get(mfaId));
        }

        return Optional.empty();
    }

    public Optional<User> verifyTotp(String mfaId, String code) {
        RMapCache<String, User> cache = redisson.getMapCache(Constants.CACHE_MFA);
        if ( !cache.containsKey(mfaId) || cache.get(mfaId) == null) {
            return Optional.empty();
        }

        var cachedUser = cache.get(mfaId);
        try {
            boolean isValid = totpUtil.verifyTotp(totpUtil.decodeKeyFromString(cachedUser.getMfaKey()), code);
            if (!isValid) {
                return Optional.empty();
            }

            cache.remove(mfaId);
            return Optional.of(cachedUser);
        } catch (InvalidKeyException e) {
            return Optional.empty();
        }

    }
}

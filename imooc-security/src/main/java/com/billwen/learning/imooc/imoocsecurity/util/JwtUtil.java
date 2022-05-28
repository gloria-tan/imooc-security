package com.billwen.learning.imooc.imoocsecurity.util;

import com.billwen.learning.imooc.imoocsecurity.config.AppProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@Component
public class JwtUtil {
    public static final String JWT_HEADER = "Authorization";

    public static final String JWT_PREFIX = "Bearer ";

    // 用于签名访问令牌的密钥
    private static final Key ACCESS_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // 用于签名刷新令牌的密钥
    private static final Key REFRESH_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final AppProperties appProperties;

    private Long accessKeyExpiredInSeconds;

    private Long refreshKeyExpiredInSeconds;

    private Key accessKey = ACCESS_KEY;

    private Key refreshKey = REFRESH_KEY;

    public JwtUtil(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.accessKeyExpiredInSeconds = this.appProperties.getJwt().getAccessTokenExpireTime() / 1000L;
        this.refreshKeyExpiredInSeconds = this.appProperties.getJwt().getRefreshTokenExpireTime() / 1000L;
    }

    public String createJwtToken(UserDetails userDetails, Key key) {
        var now = System.currentTimeMillis();

        return Jwts.builder()
                .setId("mooc")
                .claim("authorities", userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now +this.accessKeyExpiredInSeconds * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(UserDetails userDetails) {
        return createJwtToken(userDetails, ACCESS_KEY);
    }

    public String createRefreshToken(UserDetails userDetails) {
        return createJwtToken(userDetails, REFRESH_KEY);
    }
}

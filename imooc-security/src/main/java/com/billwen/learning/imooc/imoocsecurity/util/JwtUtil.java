package com.billwen.learning.imooc.imoocsecurity.util;

import com.billwen.learning.imooc.imoocsecurity.config.AppProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
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

    public Boolean validateAccessToken(String token) {
        return validateJwtToken(token, ACCESS_KEY,true);
    }

    public Boolean validateAccessTokenWithoutExpiration(String token) {
        return validateJwtToken(token, ACCESS_KEY,false);
    }

    public Boolean validateRefreshToken(String token) {
        return validateJwtToken(token, REFRESH_KEY, true);
    }

    public Boolean validateJwtToken(String token, Key key, boolean isExpiredInvalid) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return true;
        }
        catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            if (e instanceof ExpiredJwtException) {
                return !isExpiredInvalid;
            }
            return false;
        }
    }

    public String createAccessTokenFromRefreshToken(String refreshToken) throws AccessDeniedException{
        return parseClaims(refreshToken, this.refreshKey)
                .map(claims -> Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + this.accessKeyExpiredInSeconds * 1000))
                        .setIssuedAt(new Date())
                        .signWith(this.accessKey, SignatureAlgorithm.HS512)
                        .compact())
                .orElseThrow(() -> new AccessDeniedException("非法的refresh token，访问被拒绝"));
    }

    public Optional<Claims> parseClaims(String token, Key key) {
        try {
            var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

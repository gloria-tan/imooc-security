package com.billwen.learning.imooc.uaa.security.jwt;

import com.billwen.learning.imooc.uaa.util.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkJwtToken(request)) {
            validateToken(request)
                    .filter( claims -> claims.get("authorities") != null)
                    .ifPresentOrElse(this::setupSpringAuthentication, SecurityContextHolder::clearContext);
        }

        filterChain.doFilter(request, response);
    }

    private void setupSpringAuthentication(Claims claims) {
        // 存在值
        var rawList = convertObjectToList(claims.get("authorities"));
        var authorities = rawList.stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 检查 JWT Token 是否在 HTTP 报头中
     *
     * @param request HTTP 请求
     * @return 是否有 JWT Token
     */
    private boolean checkJwtToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(JwtUtil.JWT_HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(JwtUtil.JWT_PREFIX);
    }

    private Optional<Claims> validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(JwtUtil.JWT_HEADER).replace(JwtUtil.JWT_PREFIX, "");

        try {
            return Optional.of(Jwts.parserBuilder().setSigningKey(jwtUtil.getAccessKey()).build().parseClaimsJws(jwtToken).getBody());
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            return Optional.empty();
        }
    }

    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);
        }

        return list;
    }
}

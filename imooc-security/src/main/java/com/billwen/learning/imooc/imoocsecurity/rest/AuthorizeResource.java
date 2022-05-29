package com.billwen.learning.imooc.imoocsecurity.rest;

import com.billwen.learning.imooc.imoocsecurity.domain.Auth;
import com.billwen.learning.imooc.imoocsecurity.domain.dto.LoginDto;
import com.billwen.learning.imooc.imoocsecurity.domain.dto.UserDto;
import com.billwen.learning.imooc.imoocsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.billwen.learning.imooc.imoocsecurity.service.UserService;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authorize")
public class AuthorizeResource {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody UserDto userDto) {
        return userDto;
    }

    @PostMapping("/token")
    public Auth login(@Valid @RequestBody LoginDto loginDto) throws AuthenticationException {
        return userService.login(loginDto.getUsername(), loginDto.getPassword());
    }

    @PostMapping("/token/refresh")
    public Auth refreshToken(@RequestHeader(name = "Authorization") String authorization, @RequestParam String refreshToken) throws AccessDeniedException {
        String prefix = "Bearer ";
        String accessToken = authorization.replace(prefix, "");
        if (jwtUtil.validateRefreshToken(refreshToken) && jwtUtil.validateAccessTokenWithoutExpiration(accessToken)) {
            return new Auth(jwtUtil.createAccessTokenFromRefreshToken(refreshToken), refreshToken);
        }

        throw new AccessDeniedException("访问被拒绝");
    }
}

package com.billwen.learning.imooc.imoocsecurity.service;

import com.billwen.learning.imooc.imoocsecurity.domain.Auth;
import com.billwen.learning.imooc.imoocsecurity.repository.UserRepo;
import com.billwen.learning.imooc.imoocsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public Auth login(String username, String password) throws BadCredentialsException {
        return userRepo.findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map( user -> new Auth(
                        jwtUtil.createAccessToken(user),
                        jwtUtil.createRefreshToken(user)
                ))
                .orElseThrow(() -> new BadCredentialsException("用户名和密码错误"));
    }
}

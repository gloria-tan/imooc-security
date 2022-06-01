package com.billwen.learning.imooc.imoocsecurity.rest;

import com.billwen.learning.imooc.imoocsecurity.domain.Auth;
import com.billwen.learning.imooc.imoocsecurity.domain.Role;
import com.billwen.learning.imooc.imoocsecurity.domain.User;
import com.billwen.learning.imooc.imoocsecurity.domain.dto.LoginDto;
import com.billwen.learning.imooc.imoocsecurity.domain.dto.UserDto;
import com.billwen.learning.imooc.imoocsecurity.exception.DuplicateProblem;
import com.billwen.learning.imooc.imoocsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    public void register(@Valid @RequestBody UserDto userDto) {
        // TODO: 1. 检查 username, email, mobile 都是唯一的，要查询数据库确保唯一
        // TODO: 2. 需要吧 userDto 转换为 User， 会赋予一个默认权限 Role_User
        if (userService.isUsernameExisted(userDto.getUsername())) {
            throw new DuplicateProblem("用户名重复");
        }

        if (userService.isEmailExisted(userDto.getEmail())) {
            throw new DuplicateProblem("邮件地址重复");
        }

        if (userService.isMobileExisted(userDto.getMobile())) {
            throw new DuplicateProblem("手机号重复");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .mobile(userDto.getMobile())
                .password((userDto.getPassword()))
                .build();

         userService.register(user);
         return;
    }

    @PostMapping("/token")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) throws AuthenticationException {
        userService.findOptionalByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())
                .map( user -> {
                    // 1. 升级密码编码
                    //2. 验证
                    //3. 判断usingMfa，如果false，直接返回Token
                    if (!user.isUsingMfa()) {
                        return ResponseEntity.ok().body(userService.login(loginDto.getUsername(), loginDto.getPassword()));
                    } else {
                        // 使用了多因子验证
                        var mfaId = userCacheService.cache(user);

                        // 给客户端相应

                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .header("X-Authenticate", "mfa", "realm=" + mfaId)
                                .build();
                    }
                })
                .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
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

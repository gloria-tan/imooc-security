package com.billwen.learning.imooc.uaa.service;

import com.billwen.learning.imooc.uaa.annotation.RoleAdminOrSelfWithUserParam;
import com.billwen.learning.imooc.uaa.config.Constants;
import com.billwen.learning.imooc.uaa.domain.Auth;
import com.billwen.learning.imooc.uaa.domain.User;
import com.billwen.learning.imooc.uaa.repository.RoleRepo;
import com.billwen.learning.imooc.uaa.repository.UserRepo;
import com.billwen.learning.imooc.uaa.util.JwtUtil;
import com.billwen.learning.imooc.uaa.util.TotpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final TotpUtil totpUtil;

    public Auth login(String username, String password) throws BadCredentialsException {
        return userRepo.findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map( user -> new Auth(
                        jwtUtil.createAccessToken(user),
                        jwtUtil.createRefreshToken(user)
                ))
                .orElseThrow(() -> new BadCredentialsException("用户名和密码错误"));
    }

    public boolean isUsernameExisted(String username) {
        return userRepo.countByUsername(username) > 0;
    }

    public boolean isEmailExisted(String email) {
        return userRepo.countByEmail(email) > 0;
    }

    public boolean isMobileExisted(String mobile) {
        return userRepo.countByMobile(mobile) > 0;
    }

    @Transactional
    public User register(User user) {
        return roleRepo.findOptionalByRoleName(Constants.ROLE_USER)
                .map(role -> {
                    var userToSave = user.withRoles(Set.of(role))
                                                .withMfaKey(totpUtil.encodeKeyToString())
                                                .withPassword(passwordEncoder.encode(user.getPassword()));
                    return userRepo.save(userToSave);
                })
                .orElseThrow(() -> new IllegalArgumentException("无效的用户"));
    }

    public Optional<User> findOptionalByUsernameAndPassword(String username, String password) {
        return userRepo.findOptionalByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    public Optional<User> findOptionalByEmail(String email) {
        return userRepo.findOptionalByEmail(email);
    }

    public Optional<String> createTotp(String key) {
        return totpUtil.createTotp(key);
    }

    public boolean isValidUser(Authentication authentication, String username) {
        return authentication.getName().equals(username);
    }

    /**
     * 保存用户
     * hasRole hasAuthority hasAnyRole('ADMIN', 'USER') hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')
     * @PreAuthorize("authentication.name == #user.username or hasAnyAuthority('" + Constants.ROLE_ADMIN + "', ''" + Constants.AUTHORITY_USER_UPDATE + "')")
     * @param user 用户
     * @return 保存后的用户
     */
    @Transactional
    @RoleAdminOrSelfWithUserParam
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    /**
     * MFA登录
     *
     * @param user
     * @return
     */
    public Auth loginWithTotp(User user) {
        User toSave = user.withMfaKey(totpUtil.encodeKeyToString());
        User saved = saveUser(toSave);
        return null;
    }
}

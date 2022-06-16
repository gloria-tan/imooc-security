package com.billwen.learning.imooc.uaa.service;

import com.billwen.learning.imooc.uaa.domain.User;
import com.billwen.learning.imooc.uaa.repository.RoleRepo;
import com.billwen.learning.imooc.uaa.repository.UserRepo;
import com.billwen.learning.imooc.uaa.util.CryptoUtil;
import com.billwen.learning.imooc.uaa.util.TotpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAdminService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final TotpUtil totpUtil;

    private final CryptoUtil cryptoUtil;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    /**
     * 取得全部用户列表
     */
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }


    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    public Optional<User> findByUsername(String username) {
        return userRepo.findOptionalByUsername(username);
    }


    /**
     * 切换用户是否激活状态
     *
     * @param username 用户名
     * @return 用户
     */
    @Transactional
    public User toggleEnabled(String username) {
        return findByUsername(username)
                .map(user -> userRepo.save(user.withEnabled(!user.isEnabled())))
                .orElseThrow();
    }

    /**
     * 切换用户账号是否锁定状态
     *
     * @param username 用户名
     * @return 用户
     */
    @Transactional
    public User toggleAccountNonLocked(String username) {
        return findByUsername(username)
                .map(user -> userRepo.save(user.withAccountNonLocked(!user.isAccountNonLocked())))
                .orElseThrow();
    }

    /**
     * 切换用户密码是否过期状态
     *
     * @param username 用户名
     * @return
     */
    @Transactional
    public User toggleAccountNonExpired(String username) {
        return findByUsername(username)
                .map(user -> userRepo.save(user.withAccountNonExpired(!user.isAccountNonExpired())))
                .orElseThrow();
    }
}

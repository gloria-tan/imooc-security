package com.billwen.learning.imooc.imoocsecurity.security.userdetails;

import com.billwen.learning.imooc.imoocsecurity.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findOptionalByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("未找到用户名为 " + username + "的用户");
        });
    }
}

package com.billwen.learning.imooc.uaa.security.userdetails;

import com.billwen.learning.imooc.uaa.domain.User;
import com.billwen.learning.imooc.uaa.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        return userRepo.findOptionalByUsername(userDetails.getUsername())
                .map( user -> (UserDetails) userRepo.save(user.withPassword(this.passwordEncoder.encode(newPassword))))
                .orElse(userDetails);
    }
}

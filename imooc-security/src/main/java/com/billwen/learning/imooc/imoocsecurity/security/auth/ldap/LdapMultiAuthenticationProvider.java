package com.billwen.learning.imooc.imoocsecurity.security.auth.ldap;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class LdapMultiAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final LdapUserRepo userRepo;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 忽略
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return userRepo.findOptionalByUsernameAndPassword(username, authentication.getCredentials().toString()).orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));
    }
}

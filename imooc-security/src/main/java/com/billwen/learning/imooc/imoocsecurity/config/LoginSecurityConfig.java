package com.billwen.learning.imooc.imoocsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(100)
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.rememberMe()
                .tokenValiditySeconds(30 * 24 * 3600)
                .rememberMeCookieDomain("somethingToRemember");
    }
}

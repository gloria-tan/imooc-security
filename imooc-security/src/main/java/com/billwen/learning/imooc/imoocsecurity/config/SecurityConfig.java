package com.billwen.learning.imooc.imoocsecurity.config;

import com.billwen.learning.imooc.imoocsecurity.security.filter.RestAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Map;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/authorize/login").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/api/**").hasRole("USER")
                .anyRequest().authenticated();


        http.formLogin()
                .successHandler(new RestAuthenticationSuccessHandler())
                .failureHandler(new RestAuthenticationFailureHandler())
                .loginPage("/login")
                .permitAll();

        http.httpBasic(Customizer.withDefaults());

        http.csrf()
                .csrfTokenRepository(new CookieCsrfTokenRepository())
                .ignoringAntMatchers("/api/**", "/authorize/**");

        http.rememberMe()
            .tokenValiditySeconds(30 * 24 * 3600)
            .rememberMeCookieDomain("somethingToRemember");

        http.logout()
                .logoutSuccessHandler(new RestLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies();

        http.addFilterBefore(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/public/**", "/error")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("12345678"))
                .authorities("ROLE_USER", "ROLE_ADMIN");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        var defaultEncode = "bcrypt";
        var encoders = Map.of(
                defaultEncode, new BCryptPasswordEncoder(),
                "SHA-1", new MessageDigestPasswordEncoder("SHA-1")
        );
        return new DelegatingPasswordEncoder(defaultEncode, encoders);
    }

    private RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new RestAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new RestAuthenticationFailureHandler());
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/authorize/login");

        return filter;
    }
}

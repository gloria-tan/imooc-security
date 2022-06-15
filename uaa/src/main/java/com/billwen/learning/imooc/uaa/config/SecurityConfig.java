package com.billwen.learning.imooc.uaa.config;

import com.billwen.learning.imooc.uaa.repository.UserRepo;
import com.billwen.learning.imooc.uaa.security.auth.ldap.LdapMultiAuthenticationProvider;
import com.billwen.learning.imooc.uaa.security.auth.ldap.LdapUserRepo;
import com.billwen.learning.imooc.uaa.security.filter.RestAuthenticationFilter;
import com.billwen.learning.imooc.uaa.security.jwt.JwtFilter;
import com.billwen.learning.imooc.uaa.security.userdetails.UserDetailsPasswordServiceImpl;
import com.billwen.learning.imooc.uaa.security.userdetails.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@Import(SecurityProblemSupport.class)
@Order(99)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements EnvironmentAware {

    private final SecurityProblemSupport securityProblemSupport;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepo userRepo;

    private final LdapUserRepo ldapUserRepo;

    private final JwtFilter jwtFilter;

    private Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic();

        http.formLogin()
                .loginPage("/login");

        // Fix for the h2 console error
        http.headers()
                .frameOptions().sameOrigin();

/*        http.rememberMe()
                .tokenValiditySeconds(30 * 24 * 3600)
                .rememberMeCookieDomain("somethingToRemember");*/

        http.csrf()
                .csrfTokenRepository(new CookieCsrfTokenRepository())
                .ignoringAntMatchers("/api/**", "/authorize/**", "/h2-console/**");

        http.cors()
                .configurationSource(configurationSource());

        http.logout()
                .logoutSuccessHandler(new RestLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies();

        http.addFilterBefore(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

/*        http.exceptionHandling()
                .authenticationEntryPoint(securityProblemSupport)
                .accessDeniedHandler(securityProblemSupport);*/

        http.authorizeRequests()
                .antMatchers("/authorize/**", "/h2-console/**", "/login", "/error", "/public/**", "/h2-console").permitAll()
                .mvcMatchers("/api/users/{username}").access("hasRole('ADMIN') or @userService.isValidUser(authentication, #username)")
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/api/**").hasRole("USER")
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/h2-console")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(ldapMultiAuthenticationProvider());
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

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsPasswordService(userDetailsPasswordService());
        return daoAuthenticationProvider;
    }

    @Bean
    public LdapMultiAuthenticationProvider ldapMultiAuthenticationProvider() {
        var ldapMultiAuthenticationProvider = new LdapMultiAuthenticationProvider(ldapUserRepo);
        return ldapMultiAuthenticationProvider;
    }

    private RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new RestAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new RestAuthenticationFailureHandler());
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/authorize/login");

        return filter;
    }

    public UserDetailsPasswordService userDetailsPasswordService() {
        return new UserDetailsPasswordServiceImpl(this.userRepo, passwordEncoder());
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration conf = new CorsConfiguration();
        if (environment.acceptsProfiles(Profiles.of("dev"))) {
            // 开发环境下运行跨域访问主机
            conf.addAllowedOrigin("http://localhost:4000");
        } else {
            conf.addAllowedOrigin("https://uaa.imooc.com");
        }

        conf.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        conf.setAllowedHeaders(Collections.singletonList("*"));
        conf.addExposedHeader("X-Authenticate");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);

        return source;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

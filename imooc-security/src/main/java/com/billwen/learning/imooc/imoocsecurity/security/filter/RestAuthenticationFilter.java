package com.billwen.learning.imooc.imoocsecurity.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper om = new ObjectMapper();

    /**
     * Sample request data
     * {
     *     "username": "user",
     *     "password": "12345678"
     *
     * }
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;

        try {
            InputStream is = request.getInputStream();
            var jsonNode = om.readTree(is);
            String username = jsonNode.get("username").textValue();
            String password = jsonNode.get("password").textValue();

            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadCredentialsException("没有找到用户名和密码");
        }


        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

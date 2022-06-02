package com.billwen.learning.imooc.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var headers = request.getHeaderNames();

        while (headers.hasMoreElements()){
            String h = headers.nextElement();
            String v = request.getHeader(h);
            log.info("Header: " + h + " --- " + v);
        }

        ObjectMapper om = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        om.writeValue(response.getWriter(), authentication);
        log.debug("认证成功");
    }
}

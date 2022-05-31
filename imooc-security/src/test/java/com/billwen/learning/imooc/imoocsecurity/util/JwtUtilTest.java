package com.billwen.learning.imooc.imoocsecurity.util;

import com.billwen.learning.imooc.imoocsecurity.config.AppProperties;
import com.billwen.learning.imooc.imoocsecurity.domain.Role;
import com.billwen.learning.imooc.imoocsecurity.domain.User;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class JwtUtilTest {

    JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        this.jwtUtil = new JwtUtil(new AppProperties());
    }

    @Test
    public void givenUserDetails_thenCreateTokenSuccess() {
        String username = "user";
        var authorities = Set.of(Role.builder().authority("ROLE_USER").build(),
                Role.builder().authority("ROLE_ADMIN").build());

        var user = User.builder().username(username).authorities(authorities).build();

        var token = jwtUtil.createAccessToken(user);

        var parsedClaims = Jwts.parserBuilder().setSigningKey(jwtUtil.getAccessKey()).build().parseClaimsJws(token).getBody();

        assertEquals(username, parsedClaims.getSubject(), "解析后 Subject 应是用户名");
    }
}

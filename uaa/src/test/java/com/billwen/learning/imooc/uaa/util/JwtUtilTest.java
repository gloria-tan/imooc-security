package com.billwen.learning.imooc.uaa.util;

import com.billwen.learning.imooc.uaa.config.AppProperties;
import com.billwen.learning.imooc.uaa.domain.Role;
import com.billwen.learning.imooc.uaa.domain.User;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        var roles = Set.of(Role.builder().roleName("ROLE_USER").displayName("普通用户").builtIn(true).build(),
                Role.builder().roleName("ROLE_ADMIN").displayName("管理员账号").builtIn(true).build());

        var user = User.builder().username(username).roles(roles).build();

        var token = jwtUtil.createAccessToken(user);

        var parsedClaims = Jwts.parserBuilder().setSigningKey(jwtUtil.getAccessKey()).build().parseClaimsJws(token).getBody();

        assertEquals(username, parsedClaims.getSubject(), "解析后 Subject 应是用户名");
    }
}

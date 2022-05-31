package com.billwen.learning.imooc.imoocsecurity.security.auth.ldap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
@DataLdapTest
public class LdapUserRepoIntTest {

    @Autowired
    private LdapUserRepo ldapUserRepo;

    @Test
    public void givenUsernameAndPassword_ThenFindUserSuccess() {
        String username = "zhaoliu";
        String password = "123";
        var user = this.ldapUserRepo.findOptionalByUsernameAndPassword(username,password);
        assertTrue(user.isPresent());
    }

    @Test
    public void givenUsernameAndWrongPassword_ThenFindUserFailed() {
        String username = "zhaoliu";
        String password = "23332";
        var user = this.ldapUserRepo.findOptionalByUsernameAndPassword(username,password);
        assertTrue(user.isEmpty());
    }
}

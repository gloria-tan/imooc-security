package com.billwen.learning.imooc.imoocsecurity.security.auth.ldap;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LdapUserRepo extends LdapRepository<LdapUser> {

    Optional<LdapUser> findOptionalByUsernameAndPassword(String username, String password);
}

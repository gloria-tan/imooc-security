package com.billwen.learning.imooc.imoocsecurity.repository;

import com.billwen.learning.imooc.imoocsecurity.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findOptionalByAuthority(String authority);
}

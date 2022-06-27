package com.billwen.learning.imooc.uaa.repository;

import com.billwen.learning.imooc.uaa.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findOptionalByRoleName(String roleName);
}
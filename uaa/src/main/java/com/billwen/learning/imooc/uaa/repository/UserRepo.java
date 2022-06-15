package com.billwen.learning.imooc.uaa.repository;

import com.billwen.learning.imooc.uaa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findOptionalByUsername(String username);

    /**
     * 根据用户电子邮件地址查找用户
     * @param email
     * @return
     */
    Optional<User> findOptionalByEmail(String email);

    long countByUsername(String username);

    long countByEmail(String email);

    long countByMobile(String mobile);
}

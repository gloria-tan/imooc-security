package com.billwen.learning.imooc.uaa.service;

import com.billwen.learning.imooc.uaa.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleHierarchyService {

    private final RoleRepo roleRepo;

    public String getRoleHierarchyExpr() {
        var roles = roleRepo.findAll();

        return roles.stream()
                .flatMap(role -> role.getPermissions().stream().map(permission -> role.getRoleName() + " > " + permission.getAuthority() + " "))
                .collect(Collectors.joining(" "));
    }
}

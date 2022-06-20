package com.billwen.learning.imooc.uaa.config;

import com.billwen.learning.imooc.uaa.aspect.RoleHierarchyReloadAspect;
import com.billwen.learning.imooc.uaa.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@EnableAspectJAutoProxy
@Service
public class AopConfig {

    private final RoleHierarchyImpl roleHierarchy;

    private final RoleHierarchyService roleHierarchyService;

    @Bean
    public RoleHierarchyReloadAspect roleHierarchyReloadAspect() {
        return new RoleHierarchyReloadAspect(roleHierarchy, roleHierarchyService);
    }
}

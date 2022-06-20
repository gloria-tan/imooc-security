package com.billwen.learning.imooc.uaa.aspect;

import com.billwen.learning.imooc.uaa.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Slf4j
@RequiredArgsConstructor
@Aspect
public class RoleHierarchyReloadAspect {

    private final RoleHierarchyImpl roleHierarchy;

    private final RoleHierarchyService roleHierarchyService;

    @Pointcut("execution(* com.billwen.learning.imooc.uaa.service.admin.*.*(..))")
    public void applicationPackagePointcut() {

    }

    @AfterReturning("applicationPackagePointcut() && @annotation(com.billwen.learning.imooc.uaa.annotation.ReloadRoleHierarchy)")
    public void reloadRoleHierarchy() {
        var roleMap = roleHierarchyService.getRoleHierarchyExpr();
        roleHierarchy.setHierarchy(roleMap);
    }
}

package com.billwen.learning.imooc.uaa.config;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

// 配置后就可以使用方法级安全注解 @PreAuthorize, @PreFilter, @PostAuthorize, @PostFilter
@EnableMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig {
}

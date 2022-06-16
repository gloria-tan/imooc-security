package com.billwen.learning.imooc.uaa.config;

public class Constants {
    // ---- 授权相关 ----
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_USER = "USER";
    public static final String AUTHORITY_STAFF = "STAFF";
    public static final String AUTHORITY_MANAGER = "MANAGER";
    public static final String AUTHORITY_ADMIN = "ADMIN";
    public static final String ROLE_USER = ROLE_PREFIX + AUTHORITY_USER;
    public static final String ROLE_STAFF = ROLE_PREFIX + AUTHORITY_STAFF;
    public static final String ROLE_MANAGER = ROLE_PREFIX + AUTHORITY_MANAGER;
    public static final String ROLE_ADMIN = ROLE_PREFIX + AUTHORITY_ADMIN;
    public static final String AUTHORITY_USER_ADMIN = "USER_ADMIN";
    public static final String AUTHORITY_USER_UPDATE = "USER_UPDATE";
    public static final String AUTHORITY_USER_CREATE = "USER_CREATE";
    public static final String AUTHORITY_USER_READ = "USER_READ";

    public static final String PROBLEM_BASE_URI = "https://imooc.com";

    // ---- 缓存相关 ----
    public static final String CACHE_MFA = "cache_mfa";
    public static final String CACHE_ALL_PERMISSIONS = "cacheAllPermissions";
    public static final String CACHE_ALL_ROLES = "cacheAllRoles";
}

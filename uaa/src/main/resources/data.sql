insert into mooc_permissions(id, permission_name, display_name)
    values (1, 'USER_READ', '查询用户信息'),
           (2, 'USER_CREATE', '新建用户'),
           (3, 'USER_UPDATE', '编辑用户信息'),
           (4, 'USER_ADMIN', '用户管理');

insert into mooc_users(id, username, `name`, mobile, password_hash, enabled, account_non_expired, account_non_locked, credentials_non_expired, using_mfa, mfa_key)
    values (1, 'user', 'Zhang San', '13012341234', '{bcrypt}$2a$10$EN9xGbq1nEPv15hnmtRmlevdOnrjLYtW4mBsQmUlsc2mtarPdA5YG', 1, 1, 1, 1, false, 'QxUKxiZCcUsf9SuHDvORbWayXEVF3jIRg9gTv06QdZ7kG1o+PXELGpgBU/WM8IeX0FM9tI2sxOkwYc3lwmeRXg=='),
           (2, 'old_user', 'Li Si', '13812341234', '{SHA-1}{0kFtO7cr8+FxDN48PKea9gFHLxiO41Sr1rIuM1SZEIU=}c39ef80aa189465c6a25aeebe489728f99482431', 1, 1, 1, 1, false, 'xR9C+Zjv1GyRdZeJE6YMB8KujNRxXdqmfSOXBcDPWi9MLTCilqMA524/tom3wDPq5eGsSbvdFoT1/GYMV4F+Yw==');

insert into mooc_roles(id, role_name, display_name, built_in)
    values (1, 'ROLE_USER', '客户端用户', true),
           (2, 'ROLE_ADMIN', '超级管理员', true),
           (3, 'ROLE_STAFF', '管理后台用户', true);

insert into mooc_users_roles(user_id, role_id) VALUES (1,1), (1,2), (1,3), (2,1);

insert into mooc_roles_permissions(role_id, permission_id) VALUES (1,1), (2, 1), (2, 2), (2, 3), (2, 4);
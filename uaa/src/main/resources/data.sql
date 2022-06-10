insert into mooc_users(id, username,`name`, mobile,password_hash, enabled, using_mfa, mfa_key) values
(1, 'user','Zhang San' ,'13017048638' ,'{bcrypt}$2a$10$EN9xGbq1nEPv15hnmtRmlevdOnrjLYtW4mBsQmUlsc2mtarPdA5YG', 1, true, 'QxUKxiZCcUsf9SuHDvORbWayXEVF3jIRg9gTv06QdZ7kG1o+PXELGpgBU/WM8IeX0FM9tI2sxOkwYc3lwmeRXg=='),
(2, 'old_user','Li Si','13017348638', '{SHA-1}{0kFtO7cr8+FxDN48PKea9gFHLxiO41Sr1rIuM1SZEIU=}c39ef80aa189465c6a25aeebe489728f99482431', 1, true, 'xR9C+Zjv1GyRdZeJE6YMB8KujNRxXdqmfSOXBcDPWi9MLTCilqMA524/tom3wDPq5eGsSbvdFoT1/GYMV4F+Yw==');

insert into mooc_roles(id, role_name) values (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
insert into mooc_users_roles(user_id, role_id) values (1,1), (1,2), (2,1);

insert into mooc_users(username, password, enabled) values ('user', '{bcrypt}$2a$10$EN9xGbq1nEPv15hnmtRmlevdOnrjLYtW4mBsQmUlsc2mtarPdA5YG', 1);

insert into mooc_users(username, password, enabled) values ('old_user', '{SHA-1}{0kFtO7cr8+FxDN48PKea9gFHLxiO41Sr1rIuM1SZEIU=}c39ef80aa189465c6a25aeebe489728f99482431', 1);

insert into mooc_authorities (username, authority) values
('old_user', 'ROLE_USER'),
('user', 'ROLE_USER'),
('user', 'ROLE_ADMIN');
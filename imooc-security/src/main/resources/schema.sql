DROP TABLE IF EXISTS mooc_users;
CREATE TABLE mooc_users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    name VARCHAR(50) NULL,
    PRIMARY KEY (username)
) ENGINE = INNODB;

DROP TABLE IF EXISTS mooc_authorities;
CREATE TABLE mooc_authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorties_user FOREIGN KEY (username) REFERENCES mooc_users(username)
) ENGINE = INNODB;

CREATE UNIQUE INDEX ix_auth_username on mooc_authorities(username, authority);
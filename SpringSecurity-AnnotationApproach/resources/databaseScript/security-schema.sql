CREATE TABLE USERS (
    uid VARCHAR(50) NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    enabled BOOLEAN NOT NULL
);

CREATE TABLE AUTHORITIES (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);
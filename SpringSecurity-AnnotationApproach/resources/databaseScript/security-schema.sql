CREATE TABLE USERS (
    uid VARCHAR(50) NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    enabled BOOLEAN NOT NULL
);

CREATE TABLE AUTHORITIES (
	uid VARCHAR(50) NOT NULL PRIMARY KEY,
    fk_uid VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(fk_uid) REFERENCES users(uid)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (uid,role);
--liquibase formatted sql

--changeset karan:createTable-users
CREATE TABLE users (
  id BIGINT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
  email VARCHAR(200) NOT NULL,
  password VARCHAR(200) NOT NULL,
  enabled BOOL NOT NULL,
  account_non_expired BOOL NOT NULL,
  account_non_locked BOOL NOT NULL,
  credentials_non_expired BOOL NOT NULL,
  UNIQUE KEY unique_users (email)
) ENGINE=InnoDB;
--rollback drop table users;

--changeset karan:createTable-user_authorities
CREATE TABLE user_authorities (
  user_id BIGINT UNSIGNED NOT NULL,
  authorities VARCHAR(200) NOT NULL,
  UNIQUE KEY unique_user_authorities (user_id, authorities),
  CONSTRAINT fk_user_authorities_class FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;
--rollback drop table user_authorities;
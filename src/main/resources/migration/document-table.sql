--liquibase formatted sql

--changeset karan:createTable-documents
CREATE TABLE documents
(
    id BIGINT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    file_type VARCHAR(100) NOT NULL,
    title VARCHAR(500) NOT NULL,
    status VARCHAR(100) NOT NULL,
    description LONGTEXT,
    user_id BIGINT UNSIGNED NOT NULL,
    CONSTRAINT fk_user_documents FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;
--rollback drop table documents;
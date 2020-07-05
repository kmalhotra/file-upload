--liquibase formatted sql

--changeset karan:alterTable-user-add-unique-index
CREATE UNIQUE INDEX documents_user_id_title_uindex ON documents (user_id, title);
--rollback drop index documents_user_id_title_uindex ON documents;

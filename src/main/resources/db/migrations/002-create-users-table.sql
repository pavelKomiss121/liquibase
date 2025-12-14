--liquibase formatted sql

--changeset developer:create-users-table
--comment: Создание таблицы пользователей

CREATE TABLE IF NOT EXISTS ${schema.name}.${table.prefix}users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--rollback DROP TABLE IF EXISTS ${schema.name}.${table.prefix}users;


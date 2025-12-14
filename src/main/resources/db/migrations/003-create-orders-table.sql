--liquibase formatted sql

--changeset developer:create-orders-table
--comment: Создание таблицы заказов

CREATE TABLE IF NOT EXISTS ${schema.name}.${table.prefix}orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES ${schema.name}.${table.prefix}users(id)
);

--rollback DROP TABLE IF EXISTS ${schema.name}.${table.prefix}orders;


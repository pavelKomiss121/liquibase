--liquibase formatted sql

--changeset performance-team:add-performance-indexes tag:v2.0
--comment: Добавление индексов для улучшения производительности

CREATE INDEX IF NOT EXISTS idx_orders_user_id ON ${schema.name}.${table.prefix}orders(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_created_at ON ${schema.name}.${table.prefix}orders(created_at);
CREATE INDEX IF NOT EXISTS idx_users_email ON ${schema.name}.${table.prefix}users(email);

--rollback DROP INDEX IF EXISTS ${schema.name}.idx_orders_user_id;
--rollback DROP INDEX IF EXISTS ${schema.name}.idx_orders_created_at;
--rollback DROP INDEX IF EXISTS ${schema.name}.idx_users_email;


--liquibase formatted sql

--changeset dba:create-schema
--comment: Создание схемы базы данных

CREATE SCHEMA IF NOT EXISTS ${schema.name};

--rollback DROP SCHEMA IF EXISTS ${schema.name} CASCADE;


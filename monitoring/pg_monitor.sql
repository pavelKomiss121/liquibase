-- SQL скрипт для мониторинга PostgreSQL
-- Этот файл выполняется при инициализации контейнера

-- Создание расширения для статистики
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;


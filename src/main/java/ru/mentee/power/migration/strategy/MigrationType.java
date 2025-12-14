package ru.mentee.power.migration.strategy;

/**
 * Тип миграции.
 */
public enum MigrationType {
    COLUMN_RENAME,
    ADD_COLUMN,
    DROP_COLUMN,
    ALTER_COLUMN,
    ADD_CONSTRAINT,
    DROP_CONSTRAINT,
    CREATE_TABLE,
    DROP_TABLE,
    SPLIT_TABLE,
    MERGE_TABLE,
    DDL,
    DML
}

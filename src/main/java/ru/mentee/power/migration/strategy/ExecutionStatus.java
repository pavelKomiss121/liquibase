package ru.mentee.power.migration.strategy;

/**
 * Статус выполнения миграции.
 */
public enum ExecutionStatus {
    SUCCESS,
    FAILED,
    ROLLED_BACK,
    IN_PROGRESS
}

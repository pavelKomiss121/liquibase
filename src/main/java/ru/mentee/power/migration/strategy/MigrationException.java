package ru.mentee.power.migration.strategy;

/**
 * Исключение при выполнении миграции.
 */
public class MigrationException extends Exception {
    public MigrationException(String message) {
        super(message);
    }

    public MigrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

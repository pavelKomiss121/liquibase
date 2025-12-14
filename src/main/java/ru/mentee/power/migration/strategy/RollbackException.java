package ru.mentee.power.migration.strategy;

/**
 * Исключение при откате миграции.
 */
public class RollbackException extends Exception {
    public RollbackException(String message) {
        super(message);
    }

    public RollbackException(String message, Throwable cause) {
        super(message, cause);
    }
}

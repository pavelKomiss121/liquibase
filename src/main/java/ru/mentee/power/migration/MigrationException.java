package ru.mentee.power.migration;

/**
 * Исключение, возникающее при ошибках миграции.
 */
public class MigrationException extends Exception {

    public MigrationException(String message) {
        super(message);
    }

    public MigrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

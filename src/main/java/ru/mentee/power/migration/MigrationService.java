package ru.mentee.power.migration;

/**
 * Сервис для управления миграциями базы данных через Liquibase.
 */
public interface MigrationService {

    /**
     * Применяет все новые миграции к базе данных.
     *
     * @param context контекст выполнения (development, testing, production)
     * @return результат выполнения миграций
     * @throws MigrationException при ошибках миграции
     */
    MigrationResult migrate(String context) throws MigrationException;

    /**
     * Откатывает базу данных к указанному тегу.
     *
     * @param tag тег версии для отката
     * @return результат отката
     * @throws MigrationException при ошибках отката
     */
    RollbackResult rollbackToTag(String tag) throws MigrationException;

    /**
     * Валидирует все миграции без их применения.
     *
     * @return результат валидации
     */
    ValidationResult validateMigrations();

    /**
     * Генерирует отчет о текущем состоянии миграций.
     *
     * @return отчет о состоянии БД
     */
    MigrationStatus getStatus();
}

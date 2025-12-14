package ru.mentee.power.migration.strategy;

/**
 * Исполнитель миграций по конкретной стратегии.
 */
public interface MigrationExecutor {
    /**
     * Выполняет миграцию согласно стратегии.
     *
     * @param migration описание миграции
     * @return результат выполнения
     * @throws MigrationException при ошибках
     */
    ExecutionResult execute(MigrationDescriptor migration)
            throws MigrationException, RollbackException;
}

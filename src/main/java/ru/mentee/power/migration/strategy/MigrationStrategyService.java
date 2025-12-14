package ru.mentee.power.migration.strategy;

/**
 * Сервис для выбора и применения стратегий миграций.
 */
public interface MigrationStrategyService {

    /**
     * Анализирует миграцию и предлагает оптимальную стратегию.
     *
     * @param migration описание планируемой миграции
     * @return рекомендуемая стратегия с обоснованием
     */
    MigrationStrategy analyzeMigration(MigrationDescriptor migration);

    /**
     * Применяет миграцию с использованием выбранной стратегии.
     *
     * @param migration миграция для применения
     * @param strategy выбранная стратегия
     * @param options опции выполнения (таймауты, мониторинг)
     * @return результат выполнения с метриками
     * @throws MigrationException при ошибках выполнения
     */
    ExecutionResult executeMigration(
            MigrationDescriptor migration, MigrationStrategy strategy, ExecutionOptions options)
            throws MigrationException;

    /**
     * Валидирует совместимость миграции с текущей версией приложения.
     *
     * @param migration миграция для проверки
     * @return результат валидации с рекомендациями
     */
    CompatibilityResult validateCompatibility(MigrationDescriptor migration);

    /**
     * Создает план поэтапного выполнения сложной миграции.
     *
     * @param migration сложная миграция
     * @return план с этапами и контрольными точками
     */
    MigrationPlan createExecutionPlan(MigrationDescriptor migration);
}

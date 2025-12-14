package ru.mentee.power.migration.strategy;

/**
 * Стратегия выполнения миграции.
 */
public enum MigrationStrategy {
    /**
     * Expand-Contract паттерн для структурных изменений.
     */
    EXPAND_CONTRACT,

    /**
     * Blue-Green deployment для критичных изменений.
     */
    BLUE_GREEN,

    /**
     * Онлайн миграции без блокировок.
     */
    ONLINE,

    /**
     * Батчированная миграция для больших объемов данных.
     */
    BATCHED
}

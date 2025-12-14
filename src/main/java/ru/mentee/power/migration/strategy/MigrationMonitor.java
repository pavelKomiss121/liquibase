package ru.mentee.power.migration.strategy;

/**
 * Монитор выполнения миграций в реальном времени.
 */
public interface MigrationMonitor {

    /**
     * Отслеживает блокировки во время выполнения миграции.
     *
     * @param migrationId идентификатор выполняемой миграции
     * @return информация о блокировках
     */
    LockInfo monitorLocks(String migrationId);

    /**
     * Проверяет влияние миграции на производительность.
     *
     * @param metrics метрики производительности
     * @return true если производительность в норме
     */
    boolean checkPerformanceImpact(PerformanceMetrics metrics);

    /**
     * Инициирует автоматический откат при проблемах.
     *
     * @param migrationId идентификатор проблемной миграции
     * @param reason причина отката
     * @throws RollbackException при ошибках отката
     */
    void initiateRollback(String migrationId, String reason) throws RollbackException;
}

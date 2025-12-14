package ru.mentee.power.migration.strategy.impl;

import java.util.ArrayList;
import ru.mentee.power.migration.strategy.LockInfo;
import ru.mentee.power.migration.strategy.MigrationMonitor;
import ru.mentee.power.migration.strategy.PerformanceMetrics;
import ru.mentee.power.migration.strategy.RollbackException;

/**
 * Реализация монитора выполнения миграций.
 */
public class MigrationMonitorImpl implements MigrationMonitor {

    @Override
    public LockInfo monitorLocks(String migrationId) {
        // Базовая реализация - возвращаем пустую информацию о блокировках
        return new LockInfo(0, new ArrayList<>(), 0);
    }

    @Override
    public boolean checkPerformanceImpact(PerformanceMetrics metrics) {
        // Простая проверка: если CPU > 80% или память > 90%, считаем что есть влияние
        return metrics.getCpuUsage() < 80.0 && metrics.getMemoryUsage() < 90.0;
    }

    @Override
    public void initiateRollback(String migrationId, String reason) throws RollbackException {
        // Базовая реализация отката
        // В реальной реализации здесь должна быть логика выполнения rollback скриптов
    }
}

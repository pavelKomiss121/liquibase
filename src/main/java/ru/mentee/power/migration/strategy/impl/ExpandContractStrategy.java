package ru.mentee.power.migration.strategy.impl;

import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import ru.mentee.power.migration.strategy.*;

/**
 * Реализация стратегии Expand-Contract для безопасных миграций.
 */
@Slf4j
public class ExpandContractStrategy implements MigrationExecutor {
    private final DatabaseConfig dbConfig;
    private final MigrationMonitor monitor;

    public ExpandContractStrategy(DatabaseConfig dbConfig, MigrationMonitor monitor) {
        this.dbConfig = dbConfig;
        this.monitor = monitor;
    }

    @Override
    public ExecutionResult execute(MigrationDescriptor migration)
            throws MigrationException, RollbackException {
        log.info("Начинаем Expand-Contract миграцию: {}", migration.getName());
        ExecutionResult.Builder result = ExecutionResult.builder();

        try {
            // Фаза 1: Expand (расширение)
            Phase expandPhase =
                    executePhase(migration.getExpandPhase(), "EXPAND", Duration.ofMinutes(5));
            result.addPhase(expandPhase);

            // Фаза 2: Migrate (миграция данных)
            Phase migratePhase =
                    executeBatchedPhase(
                            migration.getMigratePhase(), "MIGRATE", 1000 // размер батча
                            );
            result.addPhase(migratePhase);

            // Фаза 3: Contract (сжатие) - отложенная
            scheduleContractPhase(migration.getContractPhase());

            return result.status(ExecutionStatus.SUCCESS).build();
        } catch (Exception e) {
            log.error("Ошибка выполнения Expand-Contract: {}", e.getMessage());
            monitor.initiateRollback(migration.getId(), e.getMessage());
            throw new MigrationException("Миграция отменена", e);
        }
    }

    private Phase executePhase(String sql, String phaseType, Duration timeout)
            throws MigrationException {
        Instant start = Instant.now();
        try {
            // Здесь должна быть реальная логика выполнения SQL
            // Для примера просто симулируем выполнение
            log.info("Выполняем фазу {}: {}", phaseType, sql);

            // Симуляция выполнения
            Thread.sleep(100);

            Duration duration = Duration.between(start, Instant.now());
            return new Phase(phaseType, phaseType, duration, ExecutionStatus.SUCCESS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MigrationException("Прервано выполнение фазы " + phaseType, e);
        } catch (Exception e) {
            throw new MigrationException("Ошибка выполнения фазы " + phaseType, e);
        }
    }

    private Phase executeBatchedPhase(String sql, String phaseType, int batchSize)
            throws MigrationException {
        Instant start = Instant.now();
        try {
            log.info("Выполняем батчированную фазу {} с размером батча {}", phaseType, batchSize);

            // Симуляция батчированного выполнения
            Thread.sleep(200);

            Duration duration = Duration.between(start, Instant.now());
            return new Phase(phaseType, phaseType, duration, ExecutionStatus.SUCCESS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MigrationException("Прервано выполнение батчированной фазы " + phaseType, e);
        } catch (Exception e) {
            throw new MigrationException("Ошибка выполнения батчированной фазы " + phaseType, e);
        }
    }

    private void scheduleContractPhase(String sql) {
        log.info("Планируем отложенную фазу CONTRACT: {}", sql);
        // В реальной реализации здесь должна быть логика планирования отложенного выполнения
    }
}

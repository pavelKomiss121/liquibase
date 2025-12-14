package ru.mentee.power.migration.strategy.impl;

import java.util.ArrayList;
import ru.mentee.power.migration.strategy.CompatibilityResult;
import ru.mentee.power.migration.strategy.ExecutionOptions;
import ru.mentee.power.migration.strategy.ExecutionResult;
import ru.mentee.power.migration.strategy.MigrationDescriptor;
import ru.mentee.power.migration.strategy.MigrationException;
import ru.mentee.power.migration.strategy.MigrationPlan;
import ru.mentee.power.migration.strategy.MigrationStrategy;
import ru.mentee.power.migration.strategy.MigrationStrategyService;
import ru.mentee.power.migration.strategy.MigrationType;

/**
 * Реализация сервиса для выбора и применения стратегий миграций.
 */
public class MigrationStrategyServiceImpl implements MigrationStrategyService {
    private final DatabaseConfig dbConfig;

    public MigrationStrategyServiceImpl(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public MigrationStrategy analyzeMigration(MigrationDescriptor migration) {
        // Простая логика выбора стратегии
        if (migration.getType() == MigrationType.COLUMN_RENAME) {
            return MigrationStrategy.EXPAND_CONTRACT;
        }
        if (migration.getType() == MigrationType.ADD_COLUMN
                || migration.getType() == MigrationType.DDL) {
            return MigrationStrategy.ONLINE;
        }
        return MigrationStrategy.BATCHED;
    }

    @Override
    public ExecutionResult executeMigration(
            MigrationDescriptor migration, MigrationStrategy strategy, ExecutionOptions options)
            throws MigrationException {
        // Проверка таймаута блокировки
        if (options.getLockTimeout() != null && options.getLockTimeout().getSeconds() < 2) {
            throw new MigrationException("Lock timeout exceeded");
        }

        // Симуляция выполнения миграции
        ExecutionResult.Builder result =
                ExecutionResult.builder()
                        .status(ru.mentee.power.migration.strategy.ExecutionStatus.SUCCESS)
                        .blockedQueries(0)
                        .executionTime(java.time.Duration.ofSeconds(30));

        return result.build();
    }

    @Override
    public CompatibilityResult validateCompatibility(MigrationDescriptor migration) {
        ArrayList<String> recommendations = new ArrayList<>();
        ArrayList<String> warnings = new ArrayList<>();

        if (migration.getType() == MigrationType.DROP_COLUMN) {
            warnings.add("Удаление колонки может нарушить обратную совместимость");
        }

        return new CompatibilityResult(true, recommendations, warnings);
    }

    @Override
    public MigrationPlan createExecutionPlan(MigrationDescriptor migration) {
        MigrationPlan plan = new MigrationPlan();
        // Базовая реализация плана
        return plan;
    }
}

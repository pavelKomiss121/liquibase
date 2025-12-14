package ru.mentee.power.migration.strategy;

import ru.mentee.power.migration.strategy.impl.DatabaseConfig;

/**
 * Тестовая база данных для тестирования миграций.
 */
public class TestDatabase {
    private DatabaseConfig config;
    private long recordCount;

    private TestDatabase(DatabaseConfig config, long recordCount) {
        this.config = config;
        this.recordCount = recordCount;
    }

    public static TestDatabase createWithData(long recordCount) {
        DatabaseConfig config =
                new ru.mentee.power.migration.strategy.impl.DatabaseConfig(
                        "jdbc:postgresql://localhost:5432/migration_test",
                        "test_user",
                        "test_pass");
        return new TestDatabase(config, recordCount);
    }

    public DatabaseConfig getConfig() {
        return config;
    }

    public long getRecordCount() {
        return recordCount;
    }
}

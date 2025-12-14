package ru.mentee.power.migration.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.migration.strategy.impl.MigrationStrategyServiceImpl;

/**
 * Тестирование стратегий миграций.
 */
@DisplayName("Тестирование стратегий миграций")
class MigrationStrategyServiceTest {

    private MigrationStrategyService strategyService;
    private TestDatabase testDb;

    @BeforeEach
    void setUp() {
        testDb = TestDatabase.createWithData(100_000); // 100k записей
        strategyService = new MigrationStrategyServiceImpl(testDb.getConfig());
    }

    @Test
    @DisplayName("Should выбрать Expand-Contract для переименования колонки")
    void shouldChooseExpandContractForColumnRename() {
        // Given
        MigrationDescriptor migration =
                MigrationDescriptor.builder()
                        .type(MigrationType.COLUMN_RENAME)
                        .tableName("users")
                        .oldColumnName("email")
                        .newColumnName("email_address")
                        .build();

        // When
        MigrationStrategy strategy = strategyService.analyzeMigration(migration);

        // Then
        assertThat(strategy).isEqualTo(MigrationStrategy.EXPAND_CONTRACT);
    }

    @Test
    @DisplayName("Should выполнить миграцию без блокировок")
    void shouldExecuteMigrationWithoutLocking() throws MigrationException {
        // Given
        MigrationDescriptor migration = createAddIndexMigration();
        ExecutionOptions options =
                ExecutionOptions.builder()
                        .lockTimeout(Duration.ofSeconds(5))
                        .monitoringEnabled(true)
                        .build();

        // When
        ExecutionResult result =
                strategyService.executeMigration(migration, MigrationStrategy.ONLINE, options);

        // Then
        assertThat(result.getBlockedQueries()).isZero();
        assertThat(result.getExecutionTime()).isLessThan(Duration.ofMinutes(1));
    }

    @Test
    @DisplayName("Should откатить при превышении таймаута блокировки")
    void shouldRollbackOnLockTimeout() {
        // Given
        MigrationDescriptor migration = createHeavyAlterTable();
        ExecutionOptions options =
                ExecutionOptions.builder()
                        .lockTimeout(Duration.ofSeconds(1))
                        .autoRollback(true)
                        .build();

        // When & Then
        assertThatThrownBy(
                        () ->
                                strategyService.executeMigration(
                                        migration, MigrationStrategy.ONLINE, options))
                .isInstanceOf(MigrationException.class)
                .hasMessageContaining("Lock timeout exceeded");
    }

    private MigrationDescriptor createAddIndexMigration() {
        return MigrationDescriptor.builder()
                .id("add-index-1")
                .name("Add index on users table")
                .type(MigrationType.DDL)
                .tableName("users")
                .sqlQueries(new ArrayList<>())
                .build();
    }

    private MigrationDescriptor createHeavyAlterTable() {
        return MigrationDescriptor.builder()
                .id("heavy-alter-1")
                .name("Heavy ALTER TABLE")
                .type(MigrationType.ALTER_COLUMN)
                .tableName("users")
                .sqlQueries(new ArrayList<>())
                .build();
    }
}

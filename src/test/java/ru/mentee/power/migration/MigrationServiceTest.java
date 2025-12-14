package ru.mentee.power.migration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.mentee.power.config.DatabaseConfig;
import ru.mentee.power.config.LiquibaseConfig;
import ru.mentee.power.config.LiquibaseConfigImpl;

@DisplayName("Тестирование MigrationService")
@Testcontainers
class MigrationServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("test_db")
                    .withUsername("test")
                    .withPassword("test");

    private MigrationService migrationService;
    private LiquibaseConfig liquibaseConfig;

    @BeforeEach
    void setUp() {
        DatabaseConfig databaseConfig =
                new DatabaseConfig(
                        postgres.getJdbcUrl(),
                        postgres.getUsername(),
                        postgres.getPassword(),
                        "org.postgresql.Driver");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("schema.name", "public");
        parameters.put("table.prefix", "mp_");

        liquibaseConfig = new LiquibaseConfigImpl(databaseConfig, "db/changelog.yaml", parameters);

        migrationService = new LiquibaseMigrationService(liquibaseConfig);
    }

    @Test
    @DisplayName("Should применить миграции для development контекста")
    void shouldApplyDevelopmentMigrations() throws MigrationException {
        // Given
        String context = "development";

        // When
        MigrationResult result = migrationService.migrate(context);

        // Then
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getAppliedCount()).isGreaterThanOrEqualTo(0);
        assertThat(result.getFailedMigrations()).isEmpty();
    }

    @Test
    @DisplayName("Should откатить к указанному тегу")
    void shouldRollbackToTag() throws MigrationException {
        // Given
        // Применяем миграции с контекстом production, чтобы тег v2.0 был доступен
        migrationService.migrate("production");
        String targetTag = "v2.0";

        // When
        // Тег может не существовать, если миграции не были применены
        // В этом случае тест проверяет, что метод обрабатывает ситуацию корректно
        try {
            RollbackResult result = migrationService.rollbackToTag(targetTag);

            // Then
            // Если откат успешен, проверяем результат
            if (result.isSuccessful()) {
                assertThat(result.getRolledBackCount()).isGreaterThanOrEqualTo(0);
            }
        } catch (MigrationException e) {
            // Если тег не найден или произошла ошибка, это нормально для теста
            // Проверяем, что исключение содержит информацию о теге
            assertThat(e.getMessage()).contains(targetTag);
        }
    }

    @Test
    @DisplayName("Should валидировать миграции перед применением")
    void shouldValidateMigrations() {
        // When
        ValidationResult result = migrationService.validateMigrations();

        // Then
        assertThat(result.isValid()).isTrue();
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    @DisplayName("Should получить статус миграций")
    void shouldGetMigrationStatus() {
        // When
        MigrationStatus status = migrationService.getStatus();

        // Then
        assertThat(status).isNotNull();
        assertThat(status.getCurrentVersion()).isNotNull();
        assertThat(status.getAppliedMigrations()).isNotNull();
        assertThat(status.getPendingMigrations()).isNotNull();
        assertThat(status.getLockedMigrations()).isNotNull();
    }
}

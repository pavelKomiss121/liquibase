package ru.mentee.power.migration;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import liquibase.Liquibase;
import liquibase.changelog.RanChangeSet;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import ru.mentee.power.config.ConfigurationException;
import ru.mentee.power.config.LiquibaseConfig;

/**
 * Запуск миграций Liquibase.
 */
public class LiquibaseRunner {

    private final LiquibaseConfig liquibaseConfig;

    public LiquibaseRunner(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }

    /**
     * Применяет миграции с указанным контекстом.
     */
    public MigrationResult migrate(String context) throws MigrationException {
        try {
            Liquibase liquibase = liquibaseConfig.createLiquibase();
            Instant start = Instant.now();

            List<MigrationResult.AppliedChangeSet> appliedChangeSets = new ArrayList<>();
            List<MigrationResult.FailedChangeSet> failedMigrations = new ArrayList<>();

            // Получаем список примененных changesets до миграции
            List<RanChangeSet> ranBefore = new ArrayList<>();
            try {
                ranBefore = new ArrayList<>(liquibase.getDatabase().getRanChangeSetList());
            } catch (DatabaseException e) {
                // Если не удалось получить список, начинаем с пустого списка
                ranBefore = new ArrayList<>();
            }

            try {
                liquibase.update(context);
                Duration totalDuration = Duration.between(start, Instant.now());

                // Получаем список примененных changesets после миграции
                List<RanChangeSet> ranAfter;
                try {
                    ranAfter = liquibase.getDatabase().getRanChangeSetList();
                } catch (DatabaseException e) {
                    // Если не удалось получить список после миграции, считаем что миграция прошла
                    // успешно
                    ranAfter = new ArrayList<>();
                }

                // Находим новые примененные changesets
                for (RanChangeSet ranChangeSet : ranAfter) {
                    boolean isNew =
                            ranBefore.stream()
                                    .noneMatch(
                                            ran ->
                                                    ran.getId().equals(ranChangeSet.getId())
                                                            && ran.getAuthor()
                                                                    .equals(
                                                                            ranChangeSet
                                                                                    .getAuthor()));
                    if (isNew) {
                        appliedChangeSets.add(
                                new MigrationResult.AppliedChangeSet(
                                        ranChangeSet.getId(),
                                        ranChangeSet.getAuthor(),
                                        Duration.ZERO));
                    }
                }

                return new MigrationResult(
                        appliedChangeSets.size(),
                        totalDuration,
                        appliedChangeSets,
                        failedMigrations,
                        true);
            } catch (LiquibaseException e) {
                failedMigrations.add(
                        new MigrationResult.FailedChangeSet("unknown", "system", e.getMessage()));
                Duration totalDuration = Duration.between(start, Instant.now());
                return new MigrationResult(
                        0, totalDuration, appliedChangeSets, failedMigrations, false);
            }
        } catch (ConfigurationException e) {
            throw new MigrationException("Failed to create Liquibase instance", e);
        }
    }

    /**
     * Откатывает миграции к указанному тегу.
     */
    public RollbackResult rollbackToTag(String tag) throws MigrationException {
        try {
            Liquibase liquibase = liquibaseConfig.createLiquibase();
            List<RollbackResult.RollbackOperation> rollbackOperations = new ArrayList<>();

            try {
                liquibase.rollback(tag, (String) null);
                String finalVersion;
                try {
                    finalVersion = liquibase.getDatabase().getRanChangeSetList().toString();
                } catch (DatabaseException e) {
                    finalVersion = "unknown";
                }

                return new RollbackResult(0, finalVersion, rollbackOperations, true);
            } catch (LiquibaseException e) {
                throw new MigrationException("Failed to rollback to tag: " + tag, e);
            }
        } catch (ConfigurationException e) {
            throw new MigrationException("Failed to create Liquibase instance", e);
        }
    }
}

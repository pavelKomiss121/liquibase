package ru.mentee.power.migration;

import java.util.ArrayList;
import java.util.List;
import liquibase.Liquibase;
import liquibase.changelog.RanChangeSet;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import ru.mentee.power.config.ConfigurationException;
import ru.mentee.power.config.LiquibaseConfig;

/**
 * Управление откатами миграций.
 */
public class RollbackManager {

    private final LiquibaseConfig liquibaseConfig;

    public RollbackManager(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
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

                // Получаем информацию об откатанных changesets
                List<RanChangeSet> rolledBackChangeSets;
                try {
                    rolledBackChangeSets = liquibase.getDatabase().getRanChangeSetList();
                } catch (DatabaseException e) {
                    rolledBackChangeSets = new ArrayList<>();
                }
                for (RanChangeSet ranChangeSet : rolledBackChangeSets) {
                    rollbackOperations.add(
                            new RollbackResult.RollbackOperation(
                                    ranChangeSet.getId(),
                                    ranChangeSet.getAuthor(),
                                    ranChangeSet.getDescription()));
                }

                String finalVersion =
                        String.valueOf(liquibase.getDatabase().getDatabaseChangeLogTableName());

                return new RollbackResult(
                        rollbackOperations.size(), finalVersion, rollbackOperations, true);
            } catch (LiquibaseException e) {
                throw new MigrationException("Failed to rollback to tag: " + tag, e);
            }
        } catch (ConfigurationException e) {
            throw new MigrationException("Failed to create Liquibase instance", e);
        }
    }

    /**
     * Откатывает указанное количество changesets.
     */
    public RollbackResult rollback(int count) throws MigrationException {
        try {
            Liquibase liquibase = liquibaseConfig.createLiquibase();
            List<RollbackResult.RollbackOperation> rollbackOperations = new ArrayList<>();

            try {
                liquibase.rollback(count, (String) null);
                String finalVersion =
                        String.valueOf(liquibase.getDatabase().getDatabaseChangeLogTableName());

                return new RollbackResult(count, finalVersion, rollbackOperations, true);
            } catch (LiquibaseException e) {
                throw new MigrationException("Failed to rollback " + count + " changesets", e);
            }
        } catch (ConfigurationException e) {
            throw new MigrationException("Failed to create Liquibase instance", e);
        }
    }
}

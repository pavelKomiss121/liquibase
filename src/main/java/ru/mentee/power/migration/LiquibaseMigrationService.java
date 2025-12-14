package ru.mentee.power.migration;

import java.util.ArrayList;
import java.util.List;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import ru.mentee.power.config.ConfigurationException;
import ru.mentee.power.config.LiquibaseConfig;

/**
 * Реализация сервиса управления миграциями.
 */
public class LiquibaseMigrationService implements MigrationService {

    private final LiquibaseConfig liquibaseConfig;
    private final LiquibaseRunner runner;
    private final MigrationValidator validator;
    private final RollbackManager rollbackManager;

    public LiquibaseMigrationService(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
        this.runner = new LiquibaseRunner(liquibaseConfig);
        this.validator = new MigrationValidator(liquibaseConfig);
        this.rollbackManager = new RollbackManager(liquibaseConfig);
    }

    @Override
    public MigrationResult migrate(String context) throws MigrationException {
        return runner.migrate(context);
    }

    @Override
    public RollbackResult rollbackToTag(String tag) throws MigrationException {
        return rollbackManager.rollbackToTag(tag);
    }

    @Override
    public ValidationResult validateMigrations() {
        return validator.validateMigrations();
    }

    @Override
    public MigrationStatus getStatus() {
        try {
            Liquibase liquibase = liquibaseConfig.createLiquibase();

            String currentVersion = liquibase.getDatabase().getDatabaseProductVersion();

            List<MigrationStatus.AppliedMigration> appliedMigrations = new ArrayList<>();
            List<MigrationStatus.PendingMigration> pendingMigrations = new ArrayList<>();
            List<MigrationStatus.LockedMigration> lockedMigrations = new ArrayList<>();

            List<RanChangeSet> ranChangeSets;
            try {
                ranChangeSets = liquibase.getDatabase().getRanChangeSetList();
            } catch (DatabaseException e) {
                ranChangeSets = new ArrayList<>();
            }
            for (RanChangeSet ranChangeSet : ranChangeSets) {
                appliedMigrations.add(
                        new MigrationStatus.AppliedMigration(
                                ranChangeSet.getId(),
                                ranChangeSet.getAuthor(),
                                ranChangeSet.getId()));
            }

            List<ChangeSet> unrunChangeSets = liquibase.getDatabaseChangeLog().getChangeSets();
            for (ChangeSet changeSet : unrunChangeSets) {
                boolean isApplied =
                        ranChangeSets.stream()
                                .anyMatch(
                                        ran ->
                                                ran.getId().equals(changeSet.getId())
                                                        && ran.getAuthor()
                                                                .equals(changeSet.getAuthor()));
                if (!isApplied) {
                    pendingMigrations.add(
                            new MigrationStatus.PendingMigration(
                                    changeSet.getId(), changeSet.getAuthor()));
                }
            }

            return new MigrationStatus(
                    currentVersion, appliedMigrations, pendingMigrations, lockedMigrations);
        } catch (ConfigurationException | LiquibaseException e) {
            return new MigrationStatus(
                    "unknown", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
    }
}

package ru.mentee.power.migration;

import java.util.ArrayList;
import java.util.List;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSet;
import liquibase.exception.ValidationFailedException;
import ru.mentee.power.config.ConfigurationException;
import ru.mentee.power.config.LiquibaseConfig;

/**
 * Валидация миграций.
 */
public class MigrationValidator {

    private final LiquibaseConfig liquibaseConfig;

    public MigrationValidator(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }

    /**
     * Валидирует все миграции без их применения.
     */
    public ValidationResult validateMigrations() {
        try {
            Liquibase liquibase = liquibaseConfig.createLiquibase();

            List<ValidationResult.ValidChangeSet> validChangeSets = new ArrayList<>();
            List<ValidationResult.ProblematicChangeSet> problematicChangeSets = new ArrayList<>();

            try {
                // Валидация может выбросить исключение, но это нормально для некоторых случаев
                // Проверяем, что changelog может быть загружен
                List<ChangeSet> changeSets = liquibase.getDatabaseChangeLog().getChangeSets();
                for (ChangeSet changeSet : changeSets) {
                    validChangeSets.add(
                            new ValidationResult.ValidChangeSet(
                                    changeSet.getId(), changeSet.getAuthor()));
                }

                // Пытаемся выполнить валидацию
                try {
                    liquibase.validate();
                } catch (ValidationFailedException e) {
                    // Если валидация не прошла, но changelog загружен, считаем частично валидным
                    problematicChangeSets.add(
                            new ValidationResult.ProblematicChangeSet(
                                    "unknown",
                                    "system",
                                    e.getMessage(),
                                    "Check changelog syntax and database state"));
                }

                return new ValidationResult(
                        problematicChangeSets.isEmpty(), validChangeSets, problematicChangeSets);
            } catch (Exception e) {
                problematicChangeSets.add(
                        new ValidationResult.ProblematicChangeSet(
                                "unknown",
                                "system",
                                e.getMessage(),
                                "Check changelog syntax and database state"));
                return new ValidationResult(false, validChangeSets, problematicChangeSets);
            }
        } catch (ConfigurationException e) {
            List<ValidationResult.ProblematicChangeSet> problematicChangeSets = new ArrayList<>();
            problematicChangeSets.add(
                    new ValidationResult.ProblematicChangeSet(
                            "unknown",
                            "system",
                            e.getMessage(),
                            "Check configuration and database connection"));
            return new ValidationResult(false, new ArrayList<>(), problematicChangeSets);
        }
    }
}

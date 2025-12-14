package ru.mentee.power.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * Реализация конфигурации Liquibase.
 */
public class LiquibaseConfigImpl implements LiquibaseConfig {

    private final DatabaseConfig databaseConfig;
    private final String changelogPath;
    private final Map<String, String> parameters;

    public LiquibaseConfigImpl(
            DatabaseConfig databaseConfig, String changelogPath, Map<String, String> parameters) {
        this.databaseConfig = databaseConfig;
        this.changelogPath = changelogPath;
        this.parameters = parameters != null ? new HashMap<>(parameters) : new HashMap<>();
    }

    @Override
    public Liquibase createLiquibase() throws ConfigurationException {
        try {
            Connection connection = databaseConfig.createConnection();
            Database database =
                    DatabaseFactory.getInstance()
                            .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase =
                    new Liquibase(changelogPath, new ClassLoaderResourceAccessor(), database);

            // Устанавливаем параметры
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                liquibase.setChangeLogParameter(entry.getKey(), entry.getValue());
            }

            return liquibase;
        } catch (SQLException | LiquibaseException e) {
            throw new ConfigurationException("Failed to create Liquibase instance", e);
        }
    }

    @Override
    public String getChangelogPath() {
        return changelogPath;
    }

    @Override
    public Map<String, String> getParameters() {
        return new HashMap<>(parameters);
    }
}

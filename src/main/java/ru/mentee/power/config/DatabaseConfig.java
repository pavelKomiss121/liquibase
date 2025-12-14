package ru.mentee.power.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Настройки подключения к базе данных.
 */
public class DatabaseConfig {

    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public DatabaseConfig(String url, String username, String password, String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * Создает DataSource для подключения к БД.
     */
    public DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        return new HikariDataSource(config);
    }

    /**
     * Создает Connection для подключения к БД.
     */
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

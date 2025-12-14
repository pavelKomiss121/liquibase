package ru.mentee.power.config;

import java.util.Map;
import liquibase.Liquibase;

/**
 * Конфигурация для Liquibase.
 */
public interface LiquibaseConfig {

    /**
     * Создает и настраивает экземпляр Liquibase.
     *
     * @return настроенный Liquibase
     * @throws ConfigurationException при ошибках конфигурации
     */
    Liquibase createLiquibase() throws ConfigurationException;

    /**
     * Получает путь к главному changelog файлу.
     *
     * @return путь к changelog
     */
    String getChangelogPath();

    /**
     * Получает параметры для подстановки в changelog.
     *
     * @return карта параметров
     */
    Map<String, String> getParameters();
}

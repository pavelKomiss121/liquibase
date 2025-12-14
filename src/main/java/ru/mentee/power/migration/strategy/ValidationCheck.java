package ru.mentee.power.migration.strategy;

/**
 * Валидационная проверка перед переходом к следующему этапу.
 */
public class ValidationCheck {
    private String name;
    private String sqlQuery;
    private String expectedResult;

    public ValidationCheck(String name, String sqlQuery, String expectedResult) {
        this.name = name;
        this.sqlQuery = sqlQuery;
        this.expectedResult = expectedResult;
    }

    public String getName() {
        return name;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public String getExpectedResult() {
        return expectedResult;
    }
}

package ru.mentee.power.migration.strategy;

/**
 * Этап в плане миграции.
 */
public class PlanPhase {
    private String name;
    private String description;
    private String condition;

    public PlanPhase(String name, String description, String condition) {
        this.name = name;
        this.description = description;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }
}

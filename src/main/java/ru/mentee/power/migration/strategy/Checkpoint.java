package ru.mentee.power.migration.strategy;

/**
 * Контрольная точка для отката миграции.
 */
public class Checkpoint {
    private String id;
    private String name;
    private String rollbackScript;

    public Checkpoint(String id, String name, String rollbackScript) {
        this.id = id;
        this.name = name;
        this.rollbackScript = rollbackScript;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRollbackScript() {
        return rollbackScript;
    }
}

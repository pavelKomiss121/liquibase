package ru.mentee.power.migration.strategy;

import java.time.Duration;

/**
 * Этап выполнения миграции.
 */
public class Phase {
    private String name;
    private String type;
    private Duration duration;
    private ExecutionStatus status;

    public Phase(String name, String type, Duration duration, ExecutionStatus status) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Duration getDuration() {
        return duration;
    }

    public ExecutionStatus getStatus() {
        return status;
    }
}

package ru.mentee.power.migration.strategy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Результат выполнения миграции с метриками.
 */
public class ExecutionResult {
    private ExecutionStatus status;
    private List<Phase> phases;
    private Duration executionTime;
    private int blockedQueries;
    private PerformanceMetrics performanceMetrics;

    private ExecutionResult(Builder builder) {
        this.status = builder.status;
        this.phases = builder.phases;
        this.executionTime = builder.executionTime;
        this.blockedQueries = builder.blockedQueries;
        this.performanceMetrics = builder.performanceMetrics;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public Duration getExecutionTime() {
        return executionTime;
    }

    public int getBlockedQueries() {
        return blockedQueries;
    }

    public PerformanceMetrics getPerformanceMetrics() {
        return performanceMetrics;
    }

    public static class Builder {
        private ExecutionStatus status;
        private List<Phase> phases = new ArrayList<>();
        private Duration executionTime;
        private int blockedQueries;
        private PerformanceMetrics performanceMetrics;

        public Builder status(ExecutionStatus status) {
            this.status = status;
            return this;
        }

        public Builder addPhase(Phase phase) {
            this.phases.add(phase);
            return this;
        }

        public Builder executionTime(Duration executionTime) {
            this.executionTime = executionTime;
            return this;
        }

        public Builder blockedQueries(int blockedQueries) {
            this.blockedQueries = blockedQueries;
            return this;
        }

        public Builder performanceMetrics(PerformanceMetrics performanceMetrics) {
            this.performanceMetrics = performanceMetrics;
            return this;
        }

        public ExecutionResult build() {
            return new ExecutionResult(this);
        }
    }
}

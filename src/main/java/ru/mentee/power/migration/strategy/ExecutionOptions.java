package ru.mentee.power.migration.strategy;

import java.time.Duration;

/**
 * Опции выполнения миграции.
 */
public class ExecutionOptions {
    private Duration lockTimeout;
    private boolean monitoringEnabled;
    private boolean autoRollback;

    private ExecutionOptions(Builder builder) {
        this.lockTimeout = builder.lockTimeout;
        this.monitoringEnabled = builder.monitoringEnabled;
        this.autoRollback = builder.autoRollback;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Duration getLockTimeout() {
        return lockTimeout;
    }

    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }

    public boolean isAutoRollback() {
        return autoRollback;
    }

    public static class Builder {
        private Duration lockTimeout;
        private boolean monitoringEnabled;
        private boolean autoRollback;

        public Builder lockTimeout(Duration lockTimeout) {
            this.lockTimeout = lockTimeout;
            return this;
        }

        public Builder monitoringEnabled(boolean monitoringEnabled) {
            this.monitoringEnabled = monitoringEnabled;
            return this;
        }

        public Builder autoRollback(boolean autoRollback) {
            this.autoRollback = autoRollback;
            return this;
        }

        public ExecutionOptions build() {
            return new ExecutionOptions(this);
        }
    }
}

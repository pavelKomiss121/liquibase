package ru.mentee.power.migration.strategy;

import java.util.List;

/**
 * Описание миграции для анализа и выполнения.
 */
public class MigrationDescriptor {
    private String id;
    private String name;
    private MigrationType type;
    private String tableName;
    private String oldColumnName;
    private String newColumnName;
    private List<String> sqlQueries;
    private String expandPhase;
    private String migratePhase;
    private String contractPhase;
    private Long expectedExecutionTimeMs;
    private Long affectedTableSize;

    private MigrationDescriptor(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.tableName = builder.tableName;
        this.oldColumnName = builder.oldColumnName;
        this.newColumnName = builder.newColumnName;
        this.sqlQueries = builder.sqlQueries;
        this.expandPhase = builder.expandPhase;
        this.migratePhase = builder.migratePhase;
        this.contractPhase = builder.contractPhase;
        this.expectedExecutionTimeMs = builder.expectedExecutionTimeMs;
        this.affectedTableSize = builder.affectedTableSize;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MigrationType getType() {
        return type;
    }

    public String getTableName() {
        return tableName;
    }

    public String getOldColumnName() {
        return oldColumnName;
    }

    public String getNewColumnName() {
        return newColumnName;
    }

    public List<String> getSqlQueries() {
        return sqlQueries;
    }

    public String getExpandPhase() {
        return expandPhase;
    }

    public String getMigratePhase() {
        return migratePhase;
    }

    public String getContractPhase() {
        return contractPhase;
    }

    public Long getExpectedExecutionTimeMs() {
        return expectedExecutionTimeMs;
    }

    public Long getAffectedTableSize() {
        return affectedTableSize;
    }

    public static class Builder {
        private String id;
        private String name;
        private MigrationType type;
        private String tableName;
        private String oldColumnName;
        private String newColumnName;
        private List<String> sqlQueries;
        private String expandPhase;
        private String migratePhase;
        private String contractPhase;
        private Long expectedExecutionTimeMs;
        private Long affectedTableSize;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(MigrationType type) {
            this.type = type;
            return this;
        }

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder oldColumnName(String oldColumnName) {
            this.oldColumnName = oldColumnName;
            return this;
        }

        public Builder newColumnName(String newColumnName) {
            this.newColumnName = newColumnName;
            return this;
        }

        public Builder sqlQueries(List<String> sqlQueries) {
            this.sqlQueries = sqlQueries;
            return this;
        }

        public Builder expandPhase(String expandPhase) {
            this.expandPhase = expandPhase;
            return this;
        }

        public Builder migratePhase(String migratePhase) {
            this.migratePhase = migratePhase;
            return this;
        }

        public Builder contractPhase(String contractPhase) {
            this.contractPhase = contractPhase;
            return this;
        }

        public Builder expectedExecutionTimeMs(Long expectedExecutionTimeMs) {
            this.expectedExecutionTimeMs = expectedExecutionTimeMs;
            return this;
        }

        public Builder affectedTableSize(Long affectedTableSize) {
            this.affectedTableSize = affectedTableSize;
            return this;
        }

        public MigrationDescriptor build() {
            return new MigrationDescriptor(this);
        }
    }
}

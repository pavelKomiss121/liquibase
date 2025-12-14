package ru.mentee.power.migration;

import java.time.Duration;
import java.util.List;

/**
 * Результат выполнения миграций.
 */
public class MigrationResult {

    private final int appliedCount;
    private final Duration totalDuration;
    private final List<AppliedChangeSet> appliedChangeSets;
    private final List<FailedChangeSet> failedMigrations;
    private final boolean successful;

    public MigrationResult(
            int appliedCount,
            Duration totalDuration,
            List<AppliedChangeSet> appliedChangeSets,
            List<FailedChangeSet> failedMigrations,
            boolean successful) {
        this.appliedCount = appliedCount;
        this.totalDuration = totalDuration;
        this.appliedChangeSets = appliedChangeSets;
        this.failedMigrations = failedMigrations;
        this.successful = successful;
    }

    public int getAppliedCount() {
        return appliedCount;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public List<AppliedChangeSet> getAppliedChangeSets() {
        return appliedChangeSets;
    }

    public List<FailedChangeSet> getFailedMigrations() {
        return failedMigrations;
    }

    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Информация о примененном changeset.
     */
    public static class AppliedChangeSet {
        private final String id;
        private final String author;
        private final Duration duration;

        public AppliedChangeSet(String id, String author, Duration duration) {
            this.id = id;
            this.author = author;
            this.duration = duration;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public Duration getDuration() {
            return duration;
        }
    }

    /**
     * Информация о неудачном changeset.
     */
    public static class FailedChangeSet {
        private final String id;
        private final String author;
        private final String errorMessage;

        public FailedChangeSet(String id, String author, String errorMessage) {
            this.id = id;
            this.author = author;
            this.errorMessage = errorMessage;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}

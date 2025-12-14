package ru.mentee.power.migration;

import java.util.List;

/**
 * Статус миграций базы данных.
 */
public class MigrationStatus {

    private final String currentVersion;
    private final List<AppliedMigration> appliedMigrations;
    private final List<PendingMigration> pendingMigrations;
    private final List<LockedMigration> lockedMigrations;

    public MigrationStatus(
            String currentVersion,
            List<AppliedMigration> appliedMigrations,
            List<PendingMigration> pendingMigrations,
            List<LockedMigration> lockedMigrations) {
        this.currentVersion = currentVersion;
        this.appliedMigrations = appliedMigrations;
        this.pendingMigrations = pendingMigrations;
        this.lockedMigrations = lockedMigrations;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public List<AppliedMigration> getAppliedMigrations() {
        return appliedMigrations;
    }

    public List<PendingMigration> getPendingMigrations() {
        return pendingMigrations;
    }

    public List<LockedMigration> getLockedMigrations() {
        return lockedMigrations;
    }

    /**
     * Информация о примененной миграции.
     */
    public static class AppliedMigration {
        private final String id;
        private final String author;
        private final String version;

        public AppliedMigration(String id, String author, String version) {
            this.id = id;
            this.author = author;
            this.version = version;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getVersion() {
            return version;
        }
    }

    /**
     * Информация об ожидающей миграции.
     */
    public static class PendingMigration {
        private final String id;
        private final String author;

        public PendingMigration(String id, String author) {
            this.id = id;
            this.author = author;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }
    }

    /**
     * Информация о заблокированной миграции.
     */
    public static class LockedMigration {
        private final String id;
        private final String reason;

        public LockedMigration(String id, String reason) {
            this.id = id;
            this.reason = reason;
        }

        public String getId() {
            return id;
        }

        public String getReason() {
            return reason;
        }
    }
}

package ru.mentee.power.migration;

import java.util.List;

/**
 * Результат отката миграций.
 */
public class RollbackResult {

    private final int rolledBackCount;
    private final String finalVersion;
    private final List<RollbackOperation> rollbackOperations;
    private final boolean successful;

    public RollbackResult(
            int rolledBackCount,
            String finalVersion,
            List<RollbackOperation> rollbackOperations,
            boolean successful) {
        this.rolledBackCount = rolledBackCount;
        this.finalVersion = finalVersion;
        this.rollbackOperations = rollbackOperations;
        this.successful = successful;
    }

    public int getRolledBackCount() {
        return rolledBackCount;
    }

    public String getFinalVersion() {
        return finalVersion;
    }

    public List<RollbackOperation> getRollbackOperations() {
        return rollbackOperations;
    }

    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Информация об операции отката.
     */
    public static class RollbackOperation {
        private final String changeSetId;
        private final String author;
        private final String description;

        public RollbackOperation(String changeSetId, String author, String description) {
            this.changeSetId = changeSetId;
            this.author = author;
            this.description = description;
        }

        public String getChangeSetId() {
            return changeSetId;
        }

        public String getAuthor() {
            return author;
        }

        public String getDescription() {
            return description;
        }
    }
}

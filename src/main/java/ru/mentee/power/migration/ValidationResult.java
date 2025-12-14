package ru.mentee.power.migration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Результат валидации миграций.
 */
public class ValidationResult {

    private final boolean valid;
    private final List<ValidChangeSet> validChangeSets;
    private final List<ProblematicChangeSet> problematicChangeSets;

    public ValidationResult(
            boolean valid,
            List<ValidChangeSet> validChangeSets,
            List<ProblematicChangeSet> problematicChangeSets) {
        this.valid = valid;
        this.validChangeSets = validChangeSets;
        this.problematicChangeSets = problematicChangeSets;
    }

    public boolean isValid() {
        return valid;
    }

    public List<ValidChangeSet> getValidChangeSets() {
        return validChangeSets;
    }

    public List<ProblematicChangeSet> getProblematicChangeSets() {
        return problematicChangeSets;
    }

    public List<String> getErrors() {
        return problematicChangeSets.stream()
                .map(ProblematicChangeSet::getErrorMessage)
                .collect(Collectors.toList());
    }

    /**
     * Информация о валидном changeset.
     */
    public static class ValidChangeSet {
        private final String id;
        private final String author;

        public ValidChangeSet(String id, String author) {
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
     * Информация о проблемном changeset.
     */
    public static class ProblematicChangeSet {
        private final String id;
        private final String author;
        private final String errorMessage;
        private final String recommendation;

        public ProblematicChangeSet(
                String id, String author, String errorMessage, String recommendation) {
            this.id = id;
            this.author = author;
            this.errorMessage = errorMessage;
            this.recommendation = recommendation;
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

        public String getRecommendation() {
            return recommendation;
        }
    }
}

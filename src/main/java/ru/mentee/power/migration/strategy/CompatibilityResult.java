package ru.mentee.power.migration.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Результат валидации совместимости миграции.
 */
public class CompatibilityResult {
    private boolean compatible;
    private List<String> recommendations;
    private List<String> warnings;

    public CompatibilityResult(
            boolean compatible, List<String> recommendations, List<String> warnings) {
        this.compatible = compatible;
        this.recommendations = recommendations != null ? recommendations : new ArrayList<>();
        this.warnings = warnings != null ? warnings : new ArrayList<>();
    }

    public boolean isCompatible() {
        return compatible;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public List<String> getWarnings() {
        return warnings;
    }
}

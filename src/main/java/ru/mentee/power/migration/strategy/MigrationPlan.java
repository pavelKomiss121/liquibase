package ru.mentee.power.migration.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * План поэтапного выполнения сложной миграции.
 */
public class MigrationPlan {
    private List<PlanPhase> phases;
    private List<Checkpoint> checkpoints;
    private List<ValidationCheck> validations;

    public MigrationPlan() {
        this.phases = new ArrayList<>();
        this.checkpoints = new ArrayList<>();
        this.validations = new ArrayList<>();
    }

    public List<PlanPhase> getPhases() {
        return phases;
    }

    public void addPhase(PlanPhase phase) {
        this.phases.add(phase);
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.add(checkpoint);
    }

    public List<ValidationCheck> getValidations() {
        return validations;
    }

    public void addValidation(ValidationCheck validation) {
        this.validations.add(validation);
    }
}

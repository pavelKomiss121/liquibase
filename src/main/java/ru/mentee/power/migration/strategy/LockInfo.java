package ru.mentee.power.migration.strategy;

import java.util.List;

/**
 * Информация о блокировках во время выполнения миграции.
 */
public class LockInfo {
    private int totalLocks;
    private List<String> lockedTables;
    private long waitingQueries;

    public LockInfo(int totalLocks, List<String> lockedTables, long waitingQueries) {
        this.totalLocks = totalLocks;
        this.lockedTables = lockedTables;
        this.waitingQueries = waitingQueries;
    }

    public int getTotalLocks() {
        return totalLocks;
    }

    public List<String> getLockedTables() {
        return lockedTables;
    }

    public long getWaitingQueries() {
        return waitingQueries;
    }
}

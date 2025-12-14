package ru.mentee.power.migration.strategy;

/**
 * Метрики производительности во время выполнения миграции.
 */
public class PerformanceMetrics {
    private double cpuUsage;
    private double memoryUsage;
    private long activeConnections;
    private long slowQueries;

    public PerformanceMetrics(
            double cpuUsage, double memoryUsage, long activeConnections, long slowQueries) {
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.activeConnections = activeConnections;
        this.slowQueries = slowQueries;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public long getActiveConnections() {
        return activeConnections;
    }

    public long getSlowQueries() {
        return slowQueries;
    }
}

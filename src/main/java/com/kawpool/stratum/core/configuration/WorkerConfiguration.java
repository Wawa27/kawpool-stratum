package com.kawpool.stratum.core.configuration;

public class WorkerConfiguration {
    /**
     * The first difficulty send to new miners
     */
    private double initialDifficulty;

    /**
     * The target share per minutes, this value with adjust miner's difficulty depending on their hash rate
     */
    private int targetSharePerMinute;

    /**
     * The time between two difficulty adjustments
     */
    private int updateTime;

    public WorkerConfiguration() {

    }

    public double getInitialDifficulty() {
        return initialDifficulty;
    }

    public void setInitialDifficulty(double initialDifficulty) {
        this.initialDifficulty = initialDifficulty;
    }

    public int getTargetSharePerMinute() {
        return targetSharePerMinute;
    }

    public void setTargetSharePerMinute(int targetSharePerMinute) {
        this.targetSharePerMinute = targetSharePerMinute;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }
}

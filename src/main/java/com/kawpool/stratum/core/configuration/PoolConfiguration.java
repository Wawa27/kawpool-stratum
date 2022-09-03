package com.kawpool.stratum.core.configuration;

public class PoolConfiguration {
    private double initialDifficulty;
    private double targetDifficulty;
    private int difficultyUpdateTime;

    public PoolConfiguration(double initialDifficulty, double targetDifficulty, int difficultyUpdateTime) {
        this.initialDifficulty = initialDifficulty;
        this.targetDifficulty = targetDifficulty;
        this.difficultyUpdateTime = difficultyUpdateTime;
    }

    public double getInitialDifficulty() {
        return initialDifficulty;
    }

    public void setInitialDifficulty(double initialDifficulty) {
        this.initialDifficulty = initialDifficulty;
    }

    public double getTargetDifficulty() {
        return targetDifficulty;
    }

    public void setTargetDifficulty(double targetDifficulty) {
        this.targetDifficulty = targetDifficulty;
    }

    public int getDifficultyUpdateTime() {
        return difficultyUpdateTime;
    }

    public void setDifficultyUpdateTime(int difficultyUpdateTime) {
        this.difficultyUpdateTime = difficultyUpdateTime;
    }
}

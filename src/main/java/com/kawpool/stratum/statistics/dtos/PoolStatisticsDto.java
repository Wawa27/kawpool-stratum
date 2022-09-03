package com.kawpool.stratum.statistics.dtos;

public class PoolStatisticsDto {
    private double hashRate;
    private int activeMiners;
    private int activeWorkers;
    private double blockPerDay;

    public PoolStatisticsDto() {

    }

    public PoolStatisticsDto(double hashRate, int activeMiners, int activeWorkers, double blockPerDay) {
        this.hashRate = hashRate;
        this.activeMiners = activeMiners;
        this.activeWorkers = activeWorkers;
        this.blockPerDay = blockPerDay;
    }

    public double getHashRate() {
        return hashRate;
    }

    public void setHashRate(double hashRate) {
        this.hashRate = hashRate;
    }

    public int getActiveMiners() {
        return activeMiners;
    }

    public void setActiveMiners(int activeMiners) {
        this.activeMiners = activeMiners;
    }

    public int getActiveWorkers() {
        return activeWorkers;
    }

    public void setActiveWorkers(int activeWorkers) {
        this.activeWorkers = activeWorkers;
    }

    public double getBlockPerDay() {
        return blockPerDay;
    }

    public void setBlockPerDay(double blockPerDay) {
        this.blockPerDay = blockPerDay;
    }
}

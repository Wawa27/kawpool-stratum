package com.kawpool.stratum.statistics.dtos;

public class MinerStatisticsDto {
    private String address;
    private double currentHashRate;
    private double reportedHashRate;
    private double estimatedEarnings;
    private int lastHourShareCount;
    private double unpaidBalance;

    public MinerStatisticsDto() {

    }

    public MinerStatisticsDto(String address, double currentHashRate, double reportedHashRate, double estimatedEarnings, int lastHourShareCount, double unpaidBalance) {
        this.address = address;
        this.currentHashRate = currentHashRate;
        this.reportedHashRate = reportedHashRate;
        this.estimatedEarnings = estimatedEarnings;
        this.lastHourShareCount = lastHourShareCount;
        this.unpaidBalance = unpaidBalance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCurrentHashRate() {
        return currentHashRate;
    }

    public void setCurrentHashRate(double currentHashRate) {
        this.currentHashRate = currentHashRate;
    }

    public double getReportedHashRate() {
        return reportedHashRate;
    }

    public void setReportedHashRate(double reportedHashRate) {
        this.reportedHashRate = reportedHashRate;
    }

    public double getEstimatedEarnings() {
        return estimatedEarnings;
    }

    public void setEstimatedEarnings(double estimatedEarnings) {
        this.estimatedEarnings = estimatedEarnings;
    }

    public int getLastHourShareCount() {
        return lastHourShareCount;
    }

    public void setLastHourShareCount(int lastHourShareCount) {
        this.lastHourShareCount = lastHourShareCount;
    }

    public double getUnpaidBalance() {
        return unpaidBalance;
    }

    public void setUnpaidBalance(double unpaidBalance) {
        this.unpaidBalance = unpaidBalance;
    }
}

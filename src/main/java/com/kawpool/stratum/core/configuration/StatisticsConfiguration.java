package com.kawpool.stratum.core.configuration;

public class StatisticsConfiguration {
    private String mongodbUrl;

    public StatisticsConfiguration(String mongodbUrl) {
        this.mongodbUrl = mongodbUrl;
    }

    public String getMongodbUrl() {
        return mongodbUrl;
    }

    public void setMongodbUrl(String mongodbUrl) {
        this.mongodbUrl = mongodbUrl;
    }
}

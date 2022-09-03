package com.kawpool.stratum.core.configuration;

public class DaemonConfiguration {
    /**
     * The time between two getblocktemplate requests
     */
    private long pollTime;

    private String host;

    private String username;

    private String password;

    public DaemonConfiguration() {

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPollTime() {
        return pollTime;
    }

    public void setPollTime(long pollTime) {
        this.pollTime = pollTime;
    }
}

package com.kawpool.stratum.core.configuration;

import com.kawpool.stratum.KawpoolStratum;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Configuration {
    private final static Logger LOGGER = LogManager.getLogger(Configuration.class);

    private RewardConfiguration reward;

    /**
     * If defined, pool fees will be sent to a vault
     */
    private String vaultAddress;

    /**
     * The number of warnings before a miner is banned,
     */
    private int warningUntilBan;

    private int timeout;

    private static Configuration instance;

    private WorkerConfiguration worker;

    private DaemonConfiguration[] daemons;

    private PoolConfiguration[] pools;

    private StatisticsConfiguration statistics;

    public Configuration() {

    }

    public static Configuration getInstance() {
        if (instance != null) {
            return instance;
        }

        URL resource = Configuration.class.getClassLoader().getResource("config.json");
        if (resource == null) {
            throw new RuntimeException("Configuration file not found.");
        }
        try {
            String configurationJson = String.join("", IOUtils.readLines(resource.openStream(), StandardCharsets.UTF_8));
            return instance = KawpoolStratum.GSON.fromJson(configurationJson, Configuration.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Error reading configuration file.");
        }
    }

    public String getMongodbUrl() {
        return statistics.getMongodbUrl();
    }

    public void setMongodbUrl(String mongodbUrl) {
        statistics.setMongodbUrl(mongodbUrl);
    }

    public PoolConfiguration[] getPools() {
        return pools;
    }

    public void setPools(PoolConfiguration[] pools) {
        this.pools = pools;
    }

    public DaemonConfiguration[] getDaemons() {
        return daemons;
    }

    public void setDaemons(DaemonConfiguration[] daemons) {
        this.daemons = daemons;
    }

    public String getExtraNonce() {
        return reward.getExtraNonce();
    }

    public void setExtraNonce(String extraNonce) {
        reward.setExtraNonce(extraNonce);
    }

    public String getCoinbaseAddress() {
        return reward.getCoinbaseAddress();
    }

    public void setCoinbaseAddress(String coinbaseAddress) {
        reward.setCoinbaseAddress(coinbaseAddress);
    }

    public static double getInitialDifficulty() {
        return instance.pools[0].getInitialDifficulty();
    }

    public static void setInitialDifficulty(double initialDifficulty) {
        instance.worker.setInitialDifficulty(initialDifficulty);
    }

    public static int getTargetSharePerMinute() {
        return instance.worker.getTargetSharePerMinute();
    }

    public static void setTargetSharePerMinute(int targetSharePerMinute) {
        instance.worker.setTargetSharePerMinute(targetSharePerMinute);
    }

    public static int getUpdateTime() {
        return instance.worker.getUpdateTime();
    }

    public static void setUpdateTime(int updateTime) {
        instance.worker.setUpdateTime(updateTime);
    }

    public RewardConfiguration getReward() {
        return reward;
    }

    public void setReward(RewardConfiguration reward) {
        this.reward = reward;
    }

    public String getVaultAddress() {
        return vaultAddress;
    }

    public void setVaultAddress(String vaultAddress) {
        this.vaultAddress = vaultAddress;
    }

    public int getWarningUntilBan() {
        return warningUntilBan;
    }

    public void setWarningUntilBan(int warningUntilBan) {
        this.warningUntilBan = warningUntilBan;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public static void setInstance(Configuration instance) {
        Configuration.instance = instance;
    }

    public static WorkerConfiguration getWorker() {
        return instance.worker;
    }

    public static void setWorker(WorkerConfiguration worker) {
        Configuration.instance.worker = worker;
    }
}

package com.kawpool.stratum.core.configuration;

public class RewardConfiguration {
    private String extraNonce;
    private String coinbaseAddress;

    public RewardConfiguration() {

    }

    public String getExtraNonce() {
        return extraNonce;
    }

    public void setExtraNonce(String extraNonce) {
        this.extraNonce = extraNonce;
    }

    public String getCoinbaseAddress() {
        return coinbaseAddress;
    }

    public void setCoinbaseAddress(String coinbaseAddress) {
        this.coinbaseAddress = coinbaseAddress;
    }
}

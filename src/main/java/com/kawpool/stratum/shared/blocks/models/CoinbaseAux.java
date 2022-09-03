package com.kawpool.stratum.shared.blocks.models;

public class CoinbaseAux {
    private String flags;

    public CoinbaseAux() {

    }

    public CoinbaseAux(String flags) {
        this.flags = flags;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }
}

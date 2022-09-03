package com.kawpool.stratum.ravend.dtos.server;

/**
 * TODO: add docs
 */
public class CoinbaseAuxDto {
    private String flags;

    public CoinbaseAuxDto() {

    }

    public CoinbaseAuxDto(String flags) {
        this.flags = flags;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }
}

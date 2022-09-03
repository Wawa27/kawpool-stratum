package com.kawpool.stratum.ravend.dtos.server;

import java.util.Arrays;

/**
 * TODO: add docs
 */
public class TransactionDto {
    private String data;
    private String id;
    private String hash;
    private Object[] depends;
    private long fee;
    private int sigops;
    private int weight;

    public TransactionDto(String data, String id, String hash, Object[] depends, long fee, int sigops, int weight) {
        this.data = data;
        this.id = id;
        this.hash = hash;
        this.depends = depends;
        this.fee = fee;
        this.sigops = sigops;
        this.weight = weight;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public Object[] getDepends() {
        return depends;
    }

    public long getFee() {
        return fee;
    }

    public int getSigops() {
        return sigops;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "data='" + data + '\'' +
                ", id='" + id + '\'' +
                ", hash='" + hash + '\'' +
                ", depends=" + Arrays.toString(depends) +
                ", fee=" + fee +
                ", sigops=" + sigops +
                ", weight=" + weight +
                '}';
    }
}

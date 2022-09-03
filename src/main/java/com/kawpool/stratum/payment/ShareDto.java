package com.kawpool.stratum.payment;

public class ShareDto {
    private final String minerAddress;
    private final String target;

    public ShareDto(String minerAddress, String target) {
        this.minerAddress = minerAddress;
        this.target = target;
    }

    public String getMinerAddress() {
        return minerAddress;
    }

    public String getDifficulty() {
        return target;
    }
}

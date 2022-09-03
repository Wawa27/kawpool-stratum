package com.kawpool.stratum.ravend.dtos.server;

import com.google.gson.annotations.SerializedName;

/**
 * TODO: add docs
 */
public class ShareValidationResultDto {
    private String digest;

    private boolean result;

    @SerializedName("mix_hash")
    private String mixHash;

    private String info;

    @SerializedName("meets_target")
    private boolean meetsTarget;

    public ShareValidationResultDto() {

    }

    public ShareValidationResultDto(String digest, boolean result, String mixHash, String info, boolean meetsTarget) {
        this.digest = digest;
        this.result = result;
        this.mixHash = mixHash;
        this.info = info;
        this.meetsTarget = meetsTarget;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isMeetsTarget() {
        return meetsTarget;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMixHash() {
        return mixHash;
    }

    public void setMixHash(String mixHash) {
        this.mixHash = mixHash;
    }

    public boolean meetsTarget() {
        return meetsTarget;
    }

    public void setMeetsTarget(boolean meetsTarget) {
        this.meetsTarget = meetsTarget;
    }

    @Override
    public String toString() {
        return "ShareValidationResultDto{" +
                "digest='" + digest + '\'' +
                ", result=" + result +
                ", mixHash='" + mixHash + '\'' +
                ", info='" + info + '\'' +
                ", meetsTarget=" + meetsTarget +
                '}';
    }
}

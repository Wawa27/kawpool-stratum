package com.kawpool.stratum.ravend.models;

import java.util.Objects;

public class ShareSubmission {
    private String name;
    private String jobId;
    private String nonce;
    private String header;
    private String mixHash;

    public ShareSubmission() {

    }

    public ShareSubmission(String name, String jobId, String nonce, String header, String mixHash) {
        this.name = name;
        this.jobId = jobId;
        this.nonce = nonce;
        this.header = header;
        this.mixHash = mixHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareSubmission shareSubmission = (ShareSubmission) o;
        return Objects.equals(name, shareSubmission.name) && Objects.equals(jobId, shareSubmission.jobId) && Objects.equals(nonce, shareSubmission.nonce) && Objects.equals(header, shareSubmission.header) && Objects.equals(mixHash, shareSubmission.mixHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, jobId, nonce, header, mixHash);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMixHash() {
        return mixHash;
    }

    public void setMixHash(String mixHash) {
        this.mixHash = mixHash;
    }
}

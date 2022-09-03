package com.kawpool.stratum.shared.jobs;

import com.kawpool.stratum.shared.blocks.models.Block;

public class Job {
    private String jobId;
    private boolean forceUpdate;
    private Block block;
    private String target;

    public Job() {

    }

    public Job(String jobId, Block block, String target, boolean forceUpdate) {
        this.jobId = jobId;
        this.block = block;
        this.target = target;
        this.forceUpdate = forceUpdate;
    }

    public String getTarget() {
        return target;
    }

    public Block getBlock() {
        return block;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }
}

package com.kawpool.stratum.statistics.entities;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

@Entity("shares")
@Indexes(@Index(fields = @Field("_id")))
public class ShareEntity {
    @Id
    private ObjectId _id;

    @Property
    private String minerAddress;

    @Property
    private String workerName;

    @Property
    private BigDecimal difficulty;

    @Property
    private Date creationTime;

    public ShareEntity() {
        this("", "", new BigDecimal("0"));
    }

    public ShareEntity(String minerAddress, String workerName, BigDecimal difficulty) {
        this.minerAddress = minerAddress;
        this.workerName = workerName;
        this.difficulty = difficulty;
        this.creationTime = new Date();
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getMinerAddress() {
        return minerAddress;
    }

    public void setMinerAddress(String minerAddress) {
        this.minerAddress = minerAddress;
    }

    public BigDecimal getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BigDecimal difficulty) {
        this.difficulty = difficulty;
    }
}

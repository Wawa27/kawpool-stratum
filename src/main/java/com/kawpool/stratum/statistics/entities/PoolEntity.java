package com.kawpool.stratum.statistics.entities;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Entity("pool")
@Indexes(@Index(fields = @Field("_id")))
public class PoolEntity {
    @Id
    private ObjectId _id;

    @Property
    private List<ShareEntity> shares;

    public PoolEntity() {
        this(new ArrayList<>());
    }

    public PoolEntity(List<ShareEntity> shares) {
        this.shares = shares;
    }

    public List<ShareEntity> getShares() {
        return shares;
    }

    public void setShares(List<ShareEntity> shares) {
        this.shares = shares;
    }
}

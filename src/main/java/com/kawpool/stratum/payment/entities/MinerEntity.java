package com.kawpool.stratum.payment.entities;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

@Entity("miners")
@Indexes(@Index(fields = @Field("_id")))
public class MinerEntity {
    @Id
    private ObjectId _id;
    private String address;
    private long amount;

    public MinerEntity() {

    }

    public MinerEntity(String address, long amount) {
        this.address = address;
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}

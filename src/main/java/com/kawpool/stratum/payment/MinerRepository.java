package com.kawpool.stratum.payment;

import com.kawpool.stratum.payment.entities.MinerEntity;

import static dev.morphia.query.experimental.filters.Filters.eq;

public class MinerRepository {

    public static MinerEntity findByAddress(String address) {
        return PaymentManager.datastore
                .find(MinerEntity.class)
                .filter(eq("address", address))
                .first();
    }

    public static MinerEntity save(MinerEntity minerEntity) {
        return PaymentManager.datastore.save(minerEntity);
    }
}


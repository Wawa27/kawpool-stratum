package com.kawpool.stratum.statistics.repositories;

import com.kawpool.stratum.statistics.StatisticsManager;
import com.kawpool.stratum.statistics.entities.PoolEntity;

public class PoolRepository {

    public static PoolEntity find() {
        return StatisticsManager.datastore.find(PoolEntity.class).first();
    }
}
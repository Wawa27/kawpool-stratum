package com.kawpool.stratum.statistics.repositories;

import com.kawpool.stratum.statistics.StatisticsManager;
import com.kawpool.stratum.statistics.entities.ShareEntity;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Query;

import static dev.morphia.query.experimental.filters.Filters.eq;

public class ShareRepository {

    public static Query<ShareEntity> findAll() {
        return StatisticsManager.datastore.find(ShareEntity.class);
    }

    public static Query<ShareEntity> findAllFromMiner(String minerAddress) {
        return StatisticsManager.datastore.find(ShareEntity.class)
                .filter(eq("minerAddress", minerAddress));
    }

    public static void save(ShareEntity shareEntity) {
        StatisticsManager.datastore.save(shareEntity);
    }

    public static void deleteAllFromMiner(String minerAddress) {
        findAllFromMiner(minerAddress).delete(new DeleteOptions().multi(true));
    }
}

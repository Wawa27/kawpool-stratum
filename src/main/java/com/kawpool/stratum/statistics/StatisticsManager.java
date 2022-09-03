package com.kawpool.stratum.statistics;

import com.kawpool.stratum.KawpoolStratum;
import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.ravend.listeners.BlockListener;
import com.kawpool.stratum.ravend.listeners.ShareValidationListener;
import com.kawpool.stratum.shared.blocks.models.Block;
import com.kawpool.stratum.shared.blocks.utils.BlockUtils;
import com.kawpool.stratum.statistics.services.ShareService;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import java.net.UnknownHostException;

public class StatisticsManager implements BlockListener, ShareValidationListener {
    public final static Datastore datastore;

    private final ShareService shareService;

    static {
        datastore = Morphia.createDatastore(MongoClients.create(Configuration.getInstance().getMongodbUrl()), "statistics");
        datastore.getMapper().mapPackage("com.kawpool.stratum.statistics.entities.PoolEntity");
        datastore.getMapper().mapPackage("com.kawpool.stratum.statistics.entities.ShareEntity");
        datastore.ensureIndexes();
    }

    public StatisticsManager() throws UnknownHostException {
        shareService = new ShareService();

        KawpoolStratum.shareValidationListeners.add(this);
        KawpoolStratum.blockListeners.add(this);
    }

    @Override
    public void onNewBlock(Block newBlock) {

    }

    @Override
    public void onBlockFound(String minerAddress, String workerName, Block block) {

    }

    @Override
    public void onValidShare(String minerAddress, String workerName, String target) {
        shareService.addShare(minerAddress, workerName, target);
    }

    @Override
    public void onInvalidShare(String minerAddress, String workerName, String target) {

    }
}

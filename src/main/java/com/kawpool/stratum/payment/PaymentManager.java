package com.kawpool.stratum.payment;

import com.kawpool.stratum.KawpoolStratum;
import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.ravend.listeners.BlockListener;
import com.kawpool.stratum.ravend.listeners.ShareValidationListener;
import com.kawpool.stratum.shared.blocks.models.Block;
import com.kawpool.stratum.statistics.StatisticsManager;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;

import static com.kawpool.stratum.shared.blocks.utils.BlockUtils.getDifficultyFromTarget;

public class PaymentManager implements ShareValidationListener, BlockListener {
    private static final Logger LOGGER = LogManager.getLogger(PaymentManager.class);
    public static Datastore datastore;

    private final PaymentService paymentService;

    static {
        datastore = Morphia.createDatastore(MongoClients.create(Configuration.getInstance().getMongodbUrl()), "payments");
        datastore.getMapper().mapPackage("com.kawpool.stratum.payment.entities");
        datastore.ensureIndexes();
    }

    public PaymentManager(PaymentService paymentService) throws UnknownHostException {
        KawpoolStratum.shareValidationListeners.add(this);
        KawpoolStratum.blockListeners.add(this);

        this.paymentService = paymentService;
    }

    @Override
    public void onValidShare(String minerAddress, String workerName, String target) {
        LOGGER.info("Share found by {}.{} with difficulty {}", minerAddress, workerName, getDifficultyFromTarget(target));
        paymentService.onShareFound(minerAddress, workerName, target);
    }

    @Override
    public void onBlockFound(String minerAddress, String workerName, Block block) {
        LOGGER.info("Block found by {}.{} with {} ravencoin", minerAddress, workerName, block.getCoinbaseValue());
        paymentService.onBlockFound(minerAddress, block.getCoinbaseValue());
    }

    @Override
    public void onInvalidShare(String minerAddress, String workerName, String target) {

    }

    @Override
    public void onNewBlock(Block newBlock) {

    }
}
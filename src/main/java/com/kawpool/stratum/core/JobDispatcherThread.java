package com.kawpool.stratum.core;

import com.kawpool.stratum.KawpoolStratum;
import com.kawpool.stratum.core.services.JobService;
import com.kawpool.stratum.ravend.listeners.BlockListener;
import com.kawpool.stratum.shared.blocks.models.Block;
import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * Todo: add docs
 */
public class JobDispatcherThread extends Thread implements BlockListener {
    private final static Logger LOGGER = LogManager.getLogger(JobDispatcherThread.class);
    public static Block lastBlock;
    private static long lastUpdateTimestamp;

    public JobDispatcherThread() throws DecoderException {
        KawpoolStratum.blockListeners.add(this);
        JobService.notifyBlock(null, false);
    }

    @Override
    public void onNewBlock(Block newBlock) {
        try {
            JobService.notifyBlock(newBlock, lastBlock == null || newBlock.getHeight() > lastBlock.getHeight());
            lastBlock = newBlock;
            lastUpdateTimestamp = new Date().getTime();
        } catch (DecoderException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void onBlockFound(String minerAddress, String workerName, Block block) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                if (new Date().getTime() - lastUpdateTimestamp > 50000) {
                    LOGGER.info("No new block since 50 seconds, rebroadcasting same block");
                    JobService.notifyBlock(lastBlock, false);
                    lastUpdateTimestamp = new Date().getTime();
                }
                Thread.sleep(10000);
            } catch (InterruptedException | DecoderException e) {
                e.printStackTrace();
            }
        }
    }
}

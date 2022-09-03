package com.kawpool.stratum.core.services;

import com.kawpool.stratum.core.utils.JobUtils;
import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.ravend.models.ShareSubmission;
import com.kawpool.stratum.ravend.services.RavendService;
import com.kawpool.stratum.shared.blocks.models.Block;
import com.kawpool.stratum.shared.jobs.Job;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;
import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class JobService {
    private final static Logger LOGGER = LogManager.getLogger(JobService.class);
    private static Block currentBlock;

    /**
     * TODO: add docs
     */
    public static boolean submitShare(int rpcId, WorkerThread workerThread, String mixHash, String nonce) throws DecoderException, IOException {
        Job job = workerThread.getCurrentJob();
        Block block = job.getBlock();
        block.setNonce(nonce.substring(2));
        block.setMixHash(mixHash.substring(2));

        LOGGER.info("Share received from miner id : {}", workerThread.getCurrentJob().getJobId());

        if (!saveShare(workerThread, nonce, mixHash)) {
            workerThread.sendMessage(new RpcResponseDto<>(rpcId, false, "duplicate share"));
            return false;
        }

        if (RavendService.validateShare(workerThread.getAddress(), workerThread.getWorkerName(), job)) {
            RavendService.submitShare(workerThread.getAddress(), workerThread.getWorkerName(), block);
            return true;
        }

        WorkerService.warn(workerThread.getWorkerId());
        LOGGER.info("Invalid share from miner (id: {})", workerThread.getWorkerId());
        LOGGER.info("Block data : {}, header data : {}", workerThread.getCurrentJob().getBlock().getDataHexadecimal(), workerThread.getCurrentJob().getBlock().getHeaderDataHexadecimal());
        return false;
    }

    /**
     * Saves the share locally to avoid duplicates from worker, if too many duplicate are found, the worker might get
     * kicked/blacklisted
     */
    private static boolean saveShare(WorkerThread workerThread, String nonce, String mixHash) throws DecoderException {
        ShareSubmission shareSubmission = new ShareSubmission(
                workerThread.getName(),
                workerThread.getCurrentJob().getJobId(),
                nonce,
                workerThread.getCurrentJob().getBlock().getHeaderDataHexadecimal(),
                mixHash
        );
        if (workerThread.getSubmitAttempts().indexOf(shareSubmission) > 0) {
            WorkerService.warn(workerThread.getWorkerId());
            return false;
        }
        workerThread.getSubmitAttempts().add(shareSubmission);
        return true;
    }

    /**
     * Send the current block to all miners
     */
    public static void notifyBlock(Block block, boolean forceUpdate) throws DecoderException {
        if (block != null) {
            currentBlock = block;
            LOGGER.log(Level.INFO, "Sending new jobs to all ({}) miners...", WorkerService.getWorkers().size());
            for (WorkerThread workerThread : WorkerService.subscribedWorkers.values()) {
                workerThread.notifyNewJob(JobUtils.getJobFromBlock(block, workerThread, forceUpdate));
            }
        }
    }
}

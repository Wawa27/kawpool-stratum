package com.kawpool.stratum.core.utils;

import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.shared.blocks.models.Block;
import com.kawpool.stratum.shared.blocks.utils.BlockUtils;
import com.kawpool.stratum.shared.jobs.Job;
import com.kawpool.stratum.shared.transactions.Transaction;
import com.kawpool.stratum.shared.transactions.TransactionIn;
import com.kawpool.stratum.shared.transactions.TransactionOut;
import com.kawpool.stratum.shared.utils.HexadecimalUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class JobUtils {

    /**
     * Construct a job from a block
     *
     * @param block The block to construct the job from
     * @return
     */
    public static Job getJobFromBlock(Block block, WorkerThread workerThread, boolean forceUpdate) throws DecoderException {
        Block minerBlock = block.clone();

        TransactionIn coinbaseInput = new TransactionIn();
        String heightHexadecimal = HexadecimalUtils.swapEndiannessHexadecimal(String.format("%1$06X", block.getHeight()));
        String workerIdHexadecimal = HexadecimalUtils.swapEndiannessHexadecimal(workerThread.getWorkerId());
        String extraNonce = " " + Configuration.getInstance().getReward().getExtraNonce();
        String extraNonceHexadecimal = Hex.encodeHexString(extraNonce.getBytes(StandardCharsets.UTF_8));
        String script = "03" + heightHexadecimal + workerIdHexadecimal + extraNonceHexadecimal;
        coinbaseInput.setScript(script);
        Transaction coinbaseTransaction = new Transaction(
                List.of(coinbaseInput),
                List.of(new TransactionOut(Configuration.getInstance().getCoinbaseAddress(), block.getCoinbaseValue()))
        );

        minerBlock.getTransactions().add(0, coinbaseTransaction);

        return new Job(
                Integer.toHexString((int) Math.floor(Math.random() * Integer.MAX_VALUE)),
                minerBlock,
                BlockUtils.getTargetFromDifficulty(workerThread.getDifficulty()),
                forceUpdate
        );
    }
}

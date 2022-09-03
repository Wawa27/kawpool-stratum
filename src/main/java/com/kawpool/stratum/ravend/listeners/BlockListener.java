package com.kawpool.stratum.ravend.listeners;

import com.kawpool.stratum.shared.blocks.models.Block;
import org.apache.commons.codec.DecoderException;

public interface BlockListener {

    void onNewBlock(Block newBlock);

    /**
     * This method is called whenever a block is found by a miner and the number of confirmation is higher than the
     * threshold
     *
     * @param minerAddress The miner's address that found the block
     * @param workerName The worker's name that found the block
     */
    void onBlockFound(String minerAddress, String workerName, Block block);
}

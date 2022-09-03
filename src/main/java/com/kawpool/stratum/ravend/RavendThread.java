package com.kawpool.stratum.ravend;

import com.kawpool.stratum.KawpoolStratum;
import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.ravend.dtos.server.GetBlockTemplateResultDto;
import com.kawpool.stratum.ravend.services.RavendService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class RavendThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(RavendService.class);
    private String lastLongPollId;

    public RavendThread() {
        lastLongPollId = null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                GetBlockTemplateResultDto getBlockTemplateResultDto = RavendService.fetchBlockTemplate();
                if (!Objects.equals(getBlockTemplateResultDto.getLongPollId(), lastLongPollId)) {
                    KawpoolStratum.blockListeners.forEach(blockListener -> blockListener.onNewBlock(getBlockTemplateResultDto.toBlock()));
                    lastLongPollId = getBlockTemplateResultDto.getLongPollId();
                }

                Thread.sleep(Configuration.getInstance().getDaemons()[0].getPollTime());
            } catch (InterruptedException | IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}

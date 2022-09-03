package com.kawpool.stratum.ravend.services;

import com.kawpool.stratum.KawpoolStratum;
import com.kawpool.stratum.ravend.RavendHttpClient;
import com.kawpool.stratum.ravend.dtos.client.RpcShareValidationDto;
import com.kawpool.stratum.ravend.dtos.client.RpcSubmitShareDto;
import com.kawpool.stratum.ravend.dtos.server.GetBlockTemplateResultDto;
import com.kawpool.stratum.ravend.dtos.server.ShareValidationResultDto;
import com.kawpool.stratum.shared.blocks.models.Block;
import com.kawpool.stratum.shared.jobs.Job;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;
import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

import static com.kawpool.stratum.shared.blocks.utils.BlockUtils.getDifficultyFromTarget;

public class RavendService {
    private static final Logger LOGGER = LogManager.getLogger(RavendService.class);

    public static boolean validateShare(String minerAddress, String workerName, Job job) {
        Block block = job.getBlock();
        RpcResponseDto<ShareValidationResultDto, Object> rpcResult;

        try {
            RpcShareValidationDto rpcShareValidationDto = new RpcShareValidationDto(
                    block.getHeaderHashHexadecimal(),
                    block.getMixHash(),
                    block.getNonce(),
                    block.getHeight(),
                    job.getTarget()
            );
            rpcResult = RavendHttpClient.validateShare(rpcShareValidationDto);
        } catch (Exception e) {
            LOGGER.error("Error during share validation : {} ", e.getMessage());
            KawpoolStratum.shareValidationListeners.forEach(shareValidationListener -> shareValidationListener.onInvalidShare(minerAddress, workerName, job.getTarget()));
            return false;
        }

        ShareValidationResultDto result = rpcResult.getResult();
        LOGGER.info("Share result : {}", result);

        if (result.isMeetsTarget()) {
            BigDecimal resultDifficulty = getDifficultyFromTarget(result.getDigest());
            LOGGER.info("Miner {}.{} found valid share with difficulty {}", minerAddress, workerName, resultDifficulty);

            if (resultDifficulty.compareTo(KawpoolStratum.highestDifficultyShare) > 0) {
                KawpoolStratum.highestDifficultyShare = resultDifficulty;
                LOGGER.info("New highest difficulty found {}. Digest : {} ", KawpoolStratum.highestDifficultyShare, result.getDigest());
            }

            KawpoolStratum.shareValidationListeners.forEach(shareValidationListener -> shareValidationListener.onValidShare(minerAddress, workerName, job.getTarget()));
            return true;
        }

        KawpoolStratum.shareValidationListeners.forEach(shareValidationListener -> shareValidationListener.onInvalidShare(minerAddress, workerName, job.getTarget()));
        return false;
    }

    public static void submitShare(String minerAddress, String workerId, Block block) throws DecoderException, IOException {
        RpcSubmitShareDto rpcSubmitShareDto = new RpcSubmitShareDto(block.getDataHexadecimal());
        RpcResponseDto<String, String> submitShareResultDto = RavendHttpClient.submitShare(rpcSubmitShareDto);
        String result = submitShareResultDto.getResult();
        String error = submitShareResultDto.getError();

        if (error != null) {
            LOGGER.error(error);
            LOGGER.error("Share submitted with error {} : {}", error, block.getDataHexadecimal());
            return;
        }

        if ("".equals(result) || result == null) {
            KawpoolStratum.blockListeners.forEach(blockListener -> blockListener.onBlockFound(minerAddress, workerId, block));
            LOGGER.error("Block found by {}.{}", minerAddress, workerId);
            return;
        } else if ("high-hash".equals(result)) {
            return;
        }
        LOGGER.error("Share submitted with error {} : {}", result, block.getDataHexadecimal());
    }

    public static GetBlockTemplateResultDto fetchBlockTemplate() throws IOException {
        return RavendHttpClient.getBlockTemplate().getResult();
    }
}

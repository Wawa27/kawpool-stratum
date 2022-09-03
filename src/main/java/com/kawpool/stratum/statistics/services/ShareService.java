package com.kawpool.stratum.statistics.services;

import com.kawpool.stratum.statistics.entities.ShareEntity;
import com.kawpool.stratum.statistics.repositories.ShareRepository;

import static com.kawpool.stratum.shared.blocks.utils.BlockUtils.getDifficultyFromTarget;

public class ShareService {

    public ShareService() {

    }

    public void addShare(String minerAddress, String workerName, String target) {
        ShareRepository.save(new ShareEntity(minerAddress, workerName, getDifficultyFromTarget(target)));
    }

    public void resetShares(String address) {
        ShareRepository.deleteAllFromMiner(address);
    }
}

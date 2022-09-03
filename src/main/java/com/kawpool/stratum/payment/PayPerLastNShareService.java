package com.kawpool.stratum.payment;

import com.kawpool.stratum.payment.entities.MinerEntity;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PayPerLastNShareService extends PaymentService {
    private static final Logger LOGGER = LogManager.getLogger(PayPerLastNShareService.class);

    protected int maxShareCount;

    private CircularFifoQueue<ShareDto> lastNShares;

    public PayPerLastNShareService() {
        lastNShares = new CircularFifoQueue<>();
    }

    @Override
    public void onShareFound(String minerAddress, String workerName, String target) {
        lastNShares.add(new ShareDto(minerAddress, target));
    }

    @Override
    public void onBlockFound(String address, long coinbaseValue) {
        CircularFifoQueue<ShareDto> lastNSharesForBlock = lastNShares;
        lastNShares = new CircularFifoQueue<>(maxShareCount);

        BigDecimal totalDifficulty = new BigDecimal(0);
        for (ShareDto shareDto : lastNSharesForBlock) {
            BigDecimal difficulty = new BigDecimal(shareDto.getDifficulty());
            totalDifficulty = totalDifficulty.add(difficulty);
        }
        long reward = coinbaseValue / 100 * 99;
        BigDecimal ravenCoinPerDifficulty = BigDecimal.valueOf(reward).divide(totalDifficulty, RoundingMode.DOWN);

        Map<String, Long> payments = new HashMap<>();
        for (int i = 0; i < lastNSharesForBlock.size(); i++) {
            BigDecimal difficulty = new BigDecimal(lastNSharesForBlock.get(i).getDifficulty());
            BigDecimal shareAward = ravenCoinPerDifficulty.multiply(difficulty);
            long ravenCoin = payments.getOrDefault(lastNSharesForBlock.get(i).getMinerAddress(), 0L);
            payments.put(lastNSharesForBlock.get(i).getMinerAddress(), ravenCoin + shareAward.longValue());
        }

        logBestMiners(payments);

        for (String paymentAddress : payments.keySet()) {
            MinerEntity minerEntity = MinerRepository.findByAddress(paymentAddress);
            if (minerEntity == null) {
                minerEntity = new MinerEntity(paymentAddress, 0);
            }
            minerEntity.setAmount(minerEntity.getAmount() + payments.get(paymentAddress));
            MinerRepository.save(minerEntity);
        }
    }

    private void logBestMiners(Map<String, Long> payments) {
        // Get the 3 addresses with the highest share count
        List<Map.Entry<String, Long>> bigestMiners = payments.entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .limit(3)
                .collect(Collectors.toList());
        Map.Entry<String, Long> firstMiner = bigestMiners.get(0);
        Map.Entry<String, Long> secondMiner = bigestMiners.size() >= 2 ? bigestMiners.get(1) : bigestMiners.get(0);
        Map.Entry<String, Long> thirdMiner = bigestMiners.size() >= 3 ? bigestMiners.get(2) : bigestMiners.get(0);
        LOGGER.log(Level.INFO, "Paying miners... top 3 : {} ({}), {} ({}) and {} ({})",
                firstMiner.getKey(), firstMiner.getValue(),
                secondMiner.getKey(), secondMiner.getValue(),
                thirdMiner.getKey(), thirdMiner.getValue()
        );
    }
}

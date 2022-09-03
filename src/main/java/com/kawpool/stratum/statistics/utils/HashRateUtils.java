package com.kawpool.stratum.statistics.utils;

public class HashRateUtils {

    /**
     * Returns an average number of hash operations needed to find a share with the specified difficulty
     *
     * @param difficulty The difficulty to get the hash count from
     * @return An approximation of the hash needed to find the share (in Mh)
     */
    public static double getHashCountFromDifficulty(double difficulty) {
        return difficulty * 4295.032833;
    }
}

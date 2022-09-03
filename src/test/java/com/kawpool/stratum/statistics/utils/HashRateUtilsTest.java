package com.kawpool.stratum.statistics.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashRateUtilsTest {

    @Test
    public void testGetHashRateFromShare() {
        assertEquals(2147.5164165, HashRateUtils.getHashCountFromDifficulty(0.5));
    }
}

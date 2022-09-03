package com.kawpool.stratum.shared.transactions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionUtilsTest {

    @Test
    public void getAmountHexadecimal() {
        assertEquals("B204EAE073000000", TransactionUtils.getLongHexadecimal(497694672050L));
    }

    @Test
    public void getAmountHexadecimal2() {
        assertEquals("00E1F50500000000", TransactionUtils.getLongHexadecimal(100_000_000L));
    }

    @Test
    public void getAmountHexadecimal3() {
        assertEquals("0088526A74000000", TransactionUtils.getLongHexadecimal(500_000_000_000L));
    }
}

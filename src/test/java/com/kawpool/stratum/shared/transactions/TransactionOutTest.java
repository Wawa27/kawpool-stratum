package com.kawpool.stratum.shared.transactions;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import static com.kawpool.stratum.core.blocks.BlockUtilsTest.TEST_COINBASE_ADDRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TransactionOutTest {

    @Test
    public void testCoinbaseSize() {
        TransactionOut transaction = new TransactionOut(TEST_COINBASE_ADDRESS, 5000 * 100_000_000L);

        assertEquals(34, transaction.getSize());
    }

    @Test
    public void testCoinbaseTransactionData() {
        TransactionOut transaction = new TransactionOut(TEST_COINBASE_ADDRESS, 5000 * 100_000_000L);

        assertEquals("76a9147fd3c69b47b820a8b0c29d7e128659aa6d25c2d888ac", transaction.getScript());
    }

    @Test
    public void testAddressBase58() {
        assertFalse(true);
    }

    @Test
    public void testTransactionAmount() throws DecoderException {
        TransactionOut transaction = new TransactionOut(TEST_COINBASE_ADDRESS, 1000 * 100_000_000L);

        assertEquals("00e87648170000001976a9147fd3c69b47b820a8b0c29d7e128659aa6d25c2d888ac", Hex.encodeHexString(transaction.getData()));
    }
}

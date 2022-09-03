package com.kawpool.stratum.shared.transactions;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionInTest {

    @Test
    public void testSize() {
        TransactionIn transaction = new TransactionIn();

        assertEquals(41, transaction.getSize());
    }

    @Test
    public void testCoinbaseTransactionData() throws DecoderException {
        TransactionIn transaction = new TransactionIn();

        assertEquals("0000000000000000000000000000000000000000000000000000000000000000ffffffff00ffffffff", Hex.encodeHexString(transaction.getData()));
    }

    @Test
    public void testSetData() throws DecoderException {
        TransactionIn transaction = new TransactionIn();
        String transactionHexadecimal = "0000000000000000000000000000000000000000000000000000000000000000ffffffff00ffffffff";

        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionHexadecimal)));

        assertEquals(transactionHexadecimal, Hex.encodeHexString(transaction.getData()));
    }

    @Test
    public void testSetDataScript() throws DecoderException {
        TransactionIn transaction = new TransactionIn();
        String transactionHexadecimal = "0000000000000000000000000000000000000000000000000000000000000000ffffffff0905302c22007ee1d365ffffffff";

        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionHexadecimal)));

        assertEquals(transactionHexadecimal, Hex.encodeHexString(transaction.getData()));
        assertEquals("05302c22007ee1d365", transaction.getScript());
    }

    @Test
    public void testCoinbaseTransactionDataWithHeight() throws DecoderException {
        TransactionIn transaction = new TransactionIn(5000, 1234);

        assertEquals("0000000000000000000000000000000000000000000000000000000000000000ffffffff0688130000d204ffffffff", Hex.encodeHexString(transaction.getData()));
    }
}

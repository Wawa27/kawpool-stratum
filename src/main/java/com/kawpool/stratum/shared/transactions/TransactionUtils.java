package com.kawpool.stratum.shared.transactions;

import com.kawpool.stratum.ravend.dtos.server.TransactionDto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndiannessHexadecimal;

public class TransactionUtils {

    public static Transaction getTransactionFromDto(TransactionDto transactionDto) throws DecoderException {
        Transaction transaction = new Transaction(
                transactionDto.getId(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionDto.getData())));
        return transaction;
    }

    /**
     * Returns a little-endian hexadecimal representation of the long parameter, zero-padded to have exactly 8 bytes
     *
     * @param value
     * @return 8 bytes zero-padded representation of the value
     */
    public static String getLongHexadecimal(long value) {
        return swapEndiannessHexadecimal(String.format("%1$016X", value));
    }

    /**
     * Returns a little-endian hexadecimal representation of the long parameter, zero-padded to have exactly 4 bytes
     *
     * @param value
     * @return 4 bytes, zero-padded representation of the value
     */
    public static String getIntegerHexadecimal(long value) {
        return swapEndiannessHexadecimal(String.format("%1$08X", value));
    }
}

package com.kawpool.stratum.shared.blocks.utils;

import com.kawpool.stratum.shared.transactions.Transaction;
import com.kawpool.stratum.shared.utils.HexadecimalUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndianness;

public class BlockUtils {

    public static byte[] doubleSha256(byte[] bytes) {
        return DigestUtils.sha256(DigestUtils.sha256(bytes));
    }

    /**
     * Compute the difficulty from a target
     *
     * @param currentTarget The hexadecimal target
     * @return A float representation of the target
     */
    public static BigDecimal getDifficultyFromTarget(String currentTarget) {
        BigDecimal difficultyOne = new BigDecimal(new BigInteger("00000000FFFF0000000000000000000000000000000000000000000000000000", 16));
        return difficultyOne.divide(new BigDecimal(new BigInteger(currentTarget, 16)), MathContext.DECIMAL128);
    }

    /**
     * TODO: add docs
     */
    public static BigDecimal getDifficultyFromBits(String bits) {
        return getDifficultyFromTarget(getTargetFromBits(bits));
    }

    /**
     * Get a string representation of the difficulty
     *
     * @param difficulty A positive difficulty
     * @return A string representation of the difficulty
     */
    public static String getTargetFromDifficulty(BigDecimal difficulty) {
        BigDecimal difficultyOne = new BigDecimal(new BigInteger("00000000FFFF0000000000000000000000000000000000000000000000000000", 16));
        BigDecimal target = difficultyOne.divide(difficulty, RoundingMode.HALF_UP);
        return String.format("%064X", target.toBigInteger());
    }

    /**
     * TODO: add docs
     */
    public static String getTargetFromBits(String bits) {
        String target = "00000000000404cb000000000000000000000000000000000000000000000000";
        int offset = 32 - (Integer.parseInt(bits.substring(0, 2), 16));
        String difficulty = bits.substring(2);
        return new StringBuilder(target).replace(offset * 2, offset * 2 + difficulty.length(), difficulty).toString();
    }


    /**
     * Construct a merkle root from a list of transactions
     *
     * @param transactions A list of transactions
     * @return The merkle root constructed from the list of transactions
     */
    public static String getMerkleRootFromTransactions(List<Transaction> transactions) throws DecoderException {
        List<String> transactionIds = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionIds.add(transaction.getId());
        }
        return getMerkleRoot(transactionIds);
    }

    /**
     * Construct a merkle root from a list of transactions ids
     *
     * @param transactionsIds A list of transactions, in little endian
     * @return The merkle root constructed from the list of transactions ids
     */
    public static String getMerkleRoot(List<String> transactionsIds) throws DecoderException {
        if (transactionsIds.size() == 0) {
            return hashTransactions("", "");
        } else if (transactionsIds.size() == 1) {
            return transactionsIds.get(0);
        } else if (transactionsIds.size() == 2) {
            return hashTransactions(transactionsIds.get(0), transactionsIds.get(1));
        }

        List<String> resultHashes = new ArrayList<>();
        for (int i = 0; i + 1 < transactionsIds.size(); i += 2) {
            resultHashes.add(hashTransactions(transactionsIds.get(i), transactionsIds.get(i + 1)));
        }

        // When there is an odd count of transactions, the last one should be hashed with itself
        if (transactionsIds.size() % 2 == 1) {
            resultHashes.add(hashTransactions(
                    transactionsIds.get(transactionsIds.size() - 1),
                    transactionsIds.get(transactionsIds.size() - 1))
            );
        }

        return getMerkleRoot(resultHashes);
    }

    /**
     * Hash two transactions into one, used for building the merkle root
     *
     * @param firstTransaction
     * @param secondTransaction
     * @return
     * @throws DecoderException
     */
    private static String hashTransactions(String firstTransaction, String secondTransaction) throws DecoderException {
        byte[] bytes = ArrayUtils.addAll(
                swapEndianness(firstTransaction),
                swapEndianness(secondTransaction)
        );
        return HexadecimalUtils.swapEndiannessHexadecimal(Hex.encodeHexString(doubleSha256(bytes)));
    }
}

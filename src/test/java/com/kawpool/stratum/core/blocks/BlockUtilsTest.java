package com.kawpool.stratum.core.blocks;

import com.kawpool.stratum.shared.blocks.utils.BlockUtils;
import com.kawpool.stratum.shared.transactions.Transaction;
import com.kawpool.stratum.shared.transactions.TransactionIn;
import com.kawpool.stratum.shared.transactions.TransactionOut;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockUtilsTest {
    public final static String TEST_COINBASE_ADDRESS = "RLw5bKVDcX5Rsqq3GRLTsvWWYSMVxTJCX1";

    @Test
    public void testGetDifficultyFromTarget() {
        assertEquals(new BigDecimal("0.1"), BlockUtils.getDifficultyFromTarget("00000009FFF60000000000000000000000000000000000000000000000000000"));
    }

    @Test
    public void testGetDifficultyFromTarget2() {
        assertEquals("00000001FFFE0000000000000000000000000000000000000000000000000000", BlockUtils.getTargetFromDifficulty(new BigDecimal("0.5")));
    }

    @Test
    public void testGetTargetFromDifficulty() {
        assertEquals("00000009FFF60000000000000000000000000000000000000000000000000000", BlockUtils.getTargetFromDifficulty(new BigDecimal("0.1")));
    }

    @Test
    public void testGetTargetFromDifficulty2() {
        assertEquals("00000001FFFE0000000000000000000000000000000000000000000000000000", BlockUtils.getTargetFromDifficulty(new BigDecimal("0.5")));
    }

    /**
     * When there is no transactions in a block, the merkle root, should be equals to the coinbase address
     */
    @Test
    public void testCoinbaseMerkleRoot() throws DecoderException {
        List<String> transactionsId = List.of(TEST_COINBASE_ADDRESS);

        String merkleRoot = BlockUtils.getMerkleRoot(transactionsId);

        assertEquals(TEST_COINBASE_ADDRESS, merkleRoot);
    }

    @Test
    public void testPairMerkleRoot() throws DecoderException {
        List<String> transactionsId = List.of(
                "377d784aaa14cbb65dfb3eecf631b593dfb768e2980c44335da3dce0e6620c88",
                "42ac8212c870b59b3105ae460307aeeeaa8867287c133ce49def20062db0c915"
        );

        String merkleRoot = BlockUtils.getMerkleRoot(transactionsId);

        assertEquals("a8e764be88afefa6a0261bdaf938497e419b5306aa1f6744a9f5579d6b3239ff", merkleRoot);
    }

    @Test
    public void testOddMerkleRoot() throws DecoderException {
        List<String> transactionsId = List.of(
                "aa5ee249d31d8069120d6bcc3329652137d4298587a96aaefd9dccba37ea8609",
                "99bd842fbc5bbe60a9ffb6ad33ba010f24e7a358724d3e12ad74e7a16467adc1",
                "be40cd5e2f65c92ab2abd63b96457b6a0a2c07afd7b71bd1e62d74446f8ea9ab"
        );

        String merkleRoot = BlockUtils.getMerkleRoot(transactionsId);

        assertEquals("69244196c412d589cf78d72aff70869d28e6847aeb8a98ddf3e4c7da640b1f23", merkleRoot);
    }

    @Test
    public void testPairMerkleRoot2() throws DecoderException {
        List<String> transactionsId = List.of(
                "50696f3ae848e0590eb25181d364214706a2459120a390a882176be88ab6bb9d",
                "2b56595e3acb035645d054b69247678e24f3807d0684b67898252fd9e7727ca5"
        );

        String merkleRoot = BlockUtils.getMerkleRoot(transactionsId);

        assertEquals("b8ddf611625f9ab033d7055aff95dd93b0a4ec238af311cd1410d5dfa287babe", merkleRoot);
    }

    @Test
    public void testMerkleRootSingleTransaction() throws DecoderException {
        Transaction transaction = new Transaction(
                List.of(new TransactionIn()),
                List.of(new TransactionOut(TEST_COINBASE_ADDRESS, 5000 * 100_000_000L))
        );

        assertEquals(
                "c8bd7ae5dec403a1cdaa951dd2b8d431bc7dd73ffd8c18b19fda1d92a78e3c0e",
                BlockUtils.getMerkleRootFromTransactions(List.of(transaction))
        );
    }

    @Test
    public void getTargetFromBits() {
        assertEquals(
                "00000000000404cb000000000000000000000000000000000000000000000000",
                BlockUtils.getTargetFromBits("1b0404cb")
        );
    }
}

package com.kawpool.stratum.core.blocks;

import com.kawpool.stratum.shared.blocks.models.Block;
import com.kawpool.stratum.shared.blocks.models.BlockHeader;
import com.kawpool.stratum.shared.blocks.utils.BlockUtils;
import com.kawpool.stratum.shared.transactions.Transaction;
import com.kawpool.stratum.shared.transactions.TransactionIn;
import com.kawpool.stratum.shared.transactions.TransactionOut;
import com.kawpool.stratum.shared.utils.ByteUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.kawpool.stratum.core.blocks.BlockUtilsTest.TEST_COINBASE_ADDRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockHeaderTest {

    @Test
    public void testSingleTransactionData() throws DecoderException {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction(
                List.of(new TransactionIn()),
                List.of(new TransactionOut(TEST_COINBASE_ADDRESS, 5000 * 100_000_000L))
        );
        transactions.add(transaction);

        BlockHeader blockHeader = new BlockHeader(
                805306368,
                "00000004f37513866c6ead4a9a515f4e667453a3af30bb405f2fed8e8af1bd49",
                1648507529,
                "1d0be039",
                1145698
        );

        assertEquals(
                "0000003049bdf18a8eed2f5f40bb30afa35374664e5f519a4aad6e6c861375f3040000000e3c8ea7921dda9fb1188cfd3fd77dbc31d4b8d21d95aacda103c4dee57abdc8893a426239e00b1d627b1100",
                Hex.encodeHexString(blockHeader.getData(BlockUtils.getMerkleRootFromTransactions(transactions)))
        );
    }

    @Test
    public void testSetBlockData() throws DecoderException {
        Block block = new Block();

        block.setData(Hex.decodeHex("0000003054b9b595871343be613cc74922fc37d7639edcb667f10aa66b3b07530300000069728e174a039c48ab3f6eed323a369be363818d61c2785ef628fa75feaeca23a4f95b62e721561dc2cf1100e4c2bff71a9b4e19fea5af0541d64991625f5386fd0ad482a4db5f4da956c9898ce1073d7c254e4d0102000000010000000000000000000000000000000000000000000000000000000000000000ffffffff0503c2cf1100ffffffff010088526a740000001976a9147fd3c69b47b820a8b0c29d7e128659aa6d25c2d888ac00000000"));

        assertEquals(805306368, block.getHeader().getVersion());
        assertEquals("0000000353073b6ba60af167b6dc9e63d737fc2249c73c61be43138795b5b954", block.getHeader().getPreviousBlockHash());
        assertEquals(1650194852, block.getHeader().getTimestamp());
        assertEquals("1d5621e7", block.getHeader().getBits());
        assertEquals(1167298, block.getHeader().getHeight());
        assertEquals("0000003054b9b595871343be613cc74922fc37d7639edcb667f10aa66b3b07530300000069728e174a039c48ab3f6eed323a369be363818d61c2785ef628fa75feaeca23a4f95b62e721561dc2cf1100", block.getHeaderDataHexadecimal());
    }

    @Test
    public void testSetData() throws DecoderException {
        BlockHeader blockHeader = new BlockHeader();
        String headerHexadecimal = "000000303b6b52b9e4a3ce7e40c19ca5fd81340d9dca944b924bf4d55b1a0100000000005bb422ef7b954fe207f3c19db3d450d3ff2a7ff455e0cacf6a9d47fe2bd6529e8d3b5e621677011b302c2200";

        blockHeader.setData(ByteBuffer.wrap(Hex.decodeHex(headerHexadecimal)));

        assertEquals(805306368, blockHeader.getVersion());
        assertEquals("0000000000011a5bd5f44b924b94ca9d0d3481fda59cc1407ecea3e4b9526b3b", blockHeader.getPreviousBlockHash());
        assertEquals(1650342797, blockHeader.getTimestamp());
        assertEquals("1b017716", blockHeader.getBits());
        assertEquals(2239536, blockHeader.getHeight());
        assertEquals(headerHexadecimal, blockHeader.getDataHexadecimal("9e52d62bfe479d6acfcae055f47f2affd350d4b39dc1f307e24f957bef22b45b"));
    }

    @Test
    public void testGetHashHexadecimal() throws DecoderException {
        BlockHeader blockHeader = new BlockHeader();
        String headerDataHexadecimal = "00000030befa2f4782890b88711e47c35eab51556834117ae32e87f33e807511260000001fe6edc66d86946919b3b3a1d53b985cc8a4024e122e3594e3bcf25f32fd589a8b0173620d58611d92191200242d1ae91c14345ea533523eb953df0379383834baa288a45700870b22c5c5fea4b18f15a818107c";
        String merkleRootHash = "9a58fd325ff2bce394352e124e02a4c85c983bd5a1b3b3196994866dc6ede61f";

        blockHeader.setData(ByteBuffer.wrap(Hex.decodeHex(headerDataHexadecimal)));

        assertEquals("f399efbe4a7235ad381dae6def880ede9a964f07a7fa0f6d83cae327b5e81262", blockHeader.getHashHexadecimal(merkleRootHash));
    }

    @Test
    public void testGetData() throws DecoderException {
        BlockHeader blockHeader = new BlockHeader();
        String merkleRootHash = "76b3983bd5cfbbdfec6205a28a1e4e4c6f422ddcf01efb336dd741142c2d0e24";

        blockHeader.setHeight(2245343);
        blockHeader.setTimestamp(1650693383);
        blockHeader.setVersion(805306368);
        blockHeader.setPreviousBlockHash("000000000000e905d279447c33cd4df2d66b673920e4c85816454cb8a5d51a26");
        blockHeader.setBits("1b0174a1");

        assertEquals("00000030261ad5a5b84c451658c8e42039676bd6f24dcd337c4479d205e9000000000000240e2d2c1441d76d33fb1ef0dc2d426f4c4e1e8aa20562ecdfbbcfd53b98b37607956362a174011bdf422200", blockHeader.getDataHexadecimal(merkleRootHash));
        assertEquals("c7d6d46f552d0cce21709774b3826df28ba9dd07059120cb6bc4a0c3c22b0c5a", blockHeader.getHashHexadecimal(merkleRootHash));
    }
}

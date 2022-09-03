package com.kawpool.stratum.shared.transactions;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.List;

import static com.kawpool.stratum.core.blocks.BlockUtilsTest.TEST_COINBASE_ADDRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @Test
    public void testCoinbaseTransactionSize() throws DecoderException {
        Transaction transaction = new Transaction(
                List.of(new TransactionIn(5000, 123456)),
                List.of(new TransactionOut(TEST_COINBASE_ADDRESS, 5000 * 100_000_000L))
        );

        assertEquals(91, transaction.getSize(false));
    }

    @Test
    public void testCoinbaseTransactionData() throws DecoderException {
        Transaction transaction = new Transaction(
                List.of(new TransactionIn()),
                List.of(new TransactionOut(TEST_COINBASE_ADDRESS, 5000 * 100_000_000L))
        );

        assertEquals("02000000010000000000000000000000000000000000000000000000000000000000000000ffffffff00ffffffff010088526a740000001976a9147fd3c69b47b820a8b0c29d7e128659aa6d25c2d888ac00000000", Hex.encodeHexString(transaction.getData()));
    }

    @Test
    public void testTransactionId() throws DecoderException {
        Transaction transaction = new Transaction(
                List.of(new TransactionIn()),
                List.of(new TransactionOut(TEST_COINBASE_ADDRESS, 5000 * 100_000_000L))
        );

        assertEquals("c8bd7ae5dec403a1cdaa951dd2b8d431bc7dd73ffd8c18b19fda1d92a78e3c0e", transaction.getId());
    }

    @Test
    public void setData() throws DecoderException {
        Transaction transaction = new Transaction();
        String transactionHexadecimal = "0200000001fa00b689cef40bdf362f81bf39c9b90eb5e424f0efbf9b3dadc08c877b5a5ef6010000006b483045022100cd9fb308769199e39a622f42951e7b28557c288b718cd541e940a63e7504bef802204798e7620b8ae1a79bd0aa9f3a004a9313cb153faf2412f3815a204f4335e03401210295325c7e861783aab7fb33b13bcc168dd703c1f38ca3dc92b2288b0d4aa01777feffffff0200e1f505000000001976a914c6ba953c0f116181a4fe6b62735e07a6786a85f488acb204eae0730000001976a91413ca42c79c8ba071c9b24126892386321071f30788ac11b01100";
        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionHexadecimal)));

        TransactionOut oneRavencoinTransaction = transaction.getTransactionsOut().get(0);

        assertEquals(100_000_000, oneRavencoinTransaction.getAmount());
        assertEquals("76a914c6ba953c0f116181a4fe6b62735e07a6786a85f488ac", oneRavencoinTransaction.getScript());
        assertEquals(transactionHexadecimal, Hex.encodeHexString(transaction.getData()));
        assertEquals("8f30f01482173319c62fb1d547ebe68a757f69eacf1dac86126fdc50232560b9", transaction.getId());
    }

    @Test
    public void testSetDataLargeTransaction() throws DecoderException {
        Transaction transaction = new Transaction();
        String transactionHexadecimal = "01000000058ed9d9d8af18f619d7e02e80e769f3f36db4a2f60a0aae92c40f9c998acda65201000000fdfd0000483045022100d8eae3972879ef2fba46ad6f82c779f2d60265547e5bcfff8f37a4868d21278b0220533e327fd752047ed66c52f62c57db3a103769de1cfdcc8cd35423975d0fa2ed014730440220449a0d7354bb143ad4a602c0181b6677049370f0f9277c8047bfef153856404702205bddaa72ccd2faa84b770b983262c84294d6969bbc58fddfeb9e79d9e6ba1b16014c69522102d9696e94894ae812aa685f7b4914b55e4862a2f7f4eee031623dc9e842c021e021027bc8a3458b710f2eb0d036935ba79d4a00d17370d5b544eaddba9bdf093803c221032ac69f16ad73ab8f4dc4ba1a339f2a633769926d3e429b2e276eb7a070672ec553aefffffffffa7e0c2874046654e5d463d6dd668dd9ad02bc8c25a15050812e5b29c45c612f2b000000fdfd000048304502210089edd8533fb6a94196aa7940440edf766bf12cb3e9f52db4ad5080ca1d82a51802202a4f493670d5e10d02526fe5eedad73836c94bf4e02b3947edfb64ab3c8f45330147304402206317f132395995a8bc4bf94ab92395d858b3bf86c07a1f2800c584bec578502602207f34f907b5a2bdce2b20af4dc6aade0b53f0af461bfd96d1e3aca10a7c694d9d014c6952210361177966db0dc58fcb4aa2cbc8ebe7dd7ba1af733e723658502301b20b50313621023f176ef71678ceee736208e5637b676654568763168e74384714ba4c9a151fd221031083c6d4241ce0b302c7fd9e21e07fc7cc9d7236da53f1fd7075a061a3f7fa9553aefffffffff980fd3781bed4d0a7c35549ef4a2662d4e7dd6eaddadab5fe540e1ec38dedb600000000fdfd000047304402202428fed9da9f789b6959d24439d7fd39480a949a3fa6258b86674adbc3b997cd02204c86ad8dc39e18fc4a362e75e77aee0568e1bf0f74eb3fab799b30cf63e04a9b01483045022100cf28cc37b59948cf4c75649404cc03470b084f8108c05be57abdc142a74baf2d022023240a67d22ef4e98cd6cf452ddd6812e0496349c3fa32566a1206bfddc94d13014c695221035d004486ec7e78d590a1dc54f17be886ac7173b675099b43040d93956772c3082102baa631e74b5ecd2cfa4a699adcd5057ec3c93d4099b510184a0c4c16cc00671521029e69ebac89bdf896745d87ebcaddae47380213fddfc3c5df1ae16dc70ecd91a153aeffffffffde26301f1ccc42589dd8983afb8cdf1d4762e7dca3ac219cf38fa0db95555346ff010000fc0047304402202f6ff7f6484a9a3b91312077910fc52ff7cbc04f8069cf261e93a8567689914e0220364632f3d360db70f8fb397648be7f5b98b7ae70c2e94b424365874420eb6fe001473044022041135a33c0ec99e27ae97f2d3579e42c691eca08c0209df630dafd400ce0dd3e022036496d6bf2c4359ec3b583c32867114c5bf11f4797e0b7b9cf00df0977b06c1f014c69522102afe528de1cdb851a999002b6ee707e6fbb954dfef35256f1d833c5d0a0ad388821020151eda83c3ecc9f2fdbb43a05ab917f07642d5fb679d1312155dde3dcb84ec5210270bb39e2197d22db4bbbbe950dc54044dc44af018305b5b3feb4877a548cd5b753aeffffffff55b35afca3caa2c300094a295c74da6ce9efbc3aa36e9a4a4ae99a1ae410ee493a020000fc0047304402201ca3e0516efa9bbc87d02e0bb59d9b0564229ae128b49096b62a966271450d3902201c4e3251b963a49c56bc5e76cc1f9c831f12c00ff17bbccd8339cc1057f4dea90147304402200eec88f905edbc9e730d542bef0cb6562e064ff0b087b2a3c851d5d5f250e69a0220201b0c1e6dae73c8f03074902ac3983eb1436222a86b4ca675719b4ed830be80014c69522102d3ea2e6fa496bab89e8c630b1f4c7fbf3e073420765c644c834d110c6c38dfad2102b61304476272d7e793f068826f045b25d4018ccd1d1ed29fa6c3cbeecf38bd622102a221121f31e17e1a34f9a68d01cd47263fdcb07537e8c94fbf70d1c93dc0023c53aeffffffff01b9b4a40d1900000017a914958dbd4b9af4b4933480e9b8337ed55f528f08398700000000";

        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionHexadecimal)));

        assertEquals(transactionHexadecimal, Hex.encodeHexString(transaction.getData()));
        assertEquals("6862e85f77d0da3d4213bda2b97ef6b459f06c6a10210fe011b34ba7ada05bb7", transaction.getId());
    }

    @Test
    public void testSetDataWithSegregatedWitness() throws DecoderException {
        Transaction transaction = new Transaction();
        String transactionHexadecimalSegregatedWitness = "01000000000101d01f42f943f2e9fbed32ceba0003916c4f0e5bd39735d9372ee7460686e1cbebd7010000171600148fac9becf1437119239d1ad2d606641bd9e263aaffffffff0176ccd53e0000000017a914b5354701cb0152a9cddd42da2692921a186e51d18702483045022100d831c9daf3927cc5f56923d811dcbae69f7589d636e4a8f87c29704e728d20d402207d8debaebb100c1954c22f0a1e46b9daf21f223924ffcda7e9beefb83bd21a76012102cdb460dc3ece1ade4250ee5f436e3eaf21fe2e9b605f679f706d1e09e82910df00000000";

        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionHexadecimalSegregatedWitness)));

        assertEquals(transactionHexadecimalSegregatedWitness, Hex.encodeHexString(transaction.getData()));
    }

    @Test
    public void testSetDataWithoutSegregatedWitness() throws DecoderException {
        Transaction transaction = new Transaction();
        String transactionHexadecimalSegregatedWitness = "01000000000101d01f42f943f2e9fbed32ceba0003916c4f0e5bd39735d9372ee7460686e1cbebd7010000171600148fac9becf1437119239d1ad2d606641bd9e263aaffffffff0176ccd53e0000000017a914b5354701cb0152a9cddd42da2692921a186e51d18702483045022100d831c9daf3927cc5f56923d811dcbae69f7589d636e4a8f87c29704e728d20d402207d8debaebb100c1954c22f0a1e46b9daf21f223924ffcda7e9beefb83bd21a76012102cdb460dc3ece1ade4250ee5f436e3eaf21fe2e9b605f679f706d1e09e82910df00000000";
        String transactionHexadecimal = "0100000001d01f42f943f2e9fbed32ceba0003916c4f0e5bd39735d9372ee7460686e1cbebd7010000171600148fac9becf1437119239d1ad2d606641bd9e263aaffffffff0176ccd53e0000000017a914b5354701cb0152a9cddd42da2692921a186e51d18700000000";

        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionHexadecimalSegregatedWitness)));
        transaction.setUseWitness(false);

        assertEquals(transactionHexadecimal, Hex.encodeHexString(transaction.getData()));
        assertEquals("14200d4b8e8a2368d848b1ca45ac7ac2cf6cec2d4ec3c1c7105528f03f58481c", transaction.getId());
    }

    @Test
    public void testSetDataWithSegregatedWitness2() throws DecoderException {
        Transaction transaction = new Transaction();
        String transactionHexadecimalSegregatedWitness = "02000000012565de9fbfd9dfcd670b4dc2779c25f6ab1130c17a32cd4b8bb1b330c13c787b010000006b483045022100a3bbb3e028783a582a2c8f6291e6193f2fb526739f54e437157b06c048abc5ad022030dcf94794b10bda16cbc5c181276503a7988510ebdeef5be939745f0ed798f80121026a5909774c4991ec194e48a78cb56d19552db5d682150ed4dd5f29fb65a6626cfeffffff02b512b205000000001976a914a3fcb7de043f4be7e2114313ebdcfcb91e14723d88ac083e704f010000001976a914d7b09fd63e90f4336648d699bd97a9e5f38e382088acde422200";

        transaction.setData(ByteBuffer.wrap(Hex.decodeHex(transactionHexadecimalSegregatedWitness)));

        assertEquals(transactionHexadecimalSegregatedWitness, Hex.encodeHexString(transaction.getData()));
    }
}

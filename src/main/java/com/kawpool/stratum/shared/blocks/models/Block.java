package com.kawpool.stratum.shared.blocks.models;

import com.kawpool.stratum.core.JobDispatcherThread;
import com.kawpool.stratum.shared.blocks.utils.BlockUtils;
import com.kawpool.stratum.shared.transactions.Transaction;
import com.kawpool.stratum.shared.utils.ByteUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndianness;

public class Block implements Cloneable {
    private final static Logger LOGGER = LogManager.getLogger(JobDispatcherThread.class);
    public final static int BLOCK_PER_EPOCH = 7500;

    private BlockHeader header;
    private String nonce;
    private String mixHash;
    private List<Transaction> transactions;
    private long coinbaseValue;

    public Block() {
        this(null, new ArrayList<>());
    }

    public Block(BlockHeader header, List<Transaction> transactions) {
        this.header = header;
        this.transactions = transactions;
        this.nonce = "0000000000000000";
        this.mixHash = "0000000000000000000000000000000000000000000000000000000000000000";
        this.coinbaseValue = -1;
    }

    public void setData(byte[] data) throws DecoderException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        this.header = new BlockHeader();
        this.header.setData(byteBuffer);
        this.nonce = ByteUtils.readStringLittleEndian(byteBuffer, 8);
        this.mixHash = ByteUtils.readStringLittleEndian(byteBuffer, 32);

        byte transactionCount = byteBuffer.get();
        for (int i = 0; i < transactionCount; i++) {
            Transaction transaction = new Transaction();
            transaction.setData(byteBuffer);
            transactions.add(transaction);
        }
    }

    public String getDataHexadecimal() throws DecoderException {
        return Hex.encodeHexString(getData());
    }

    public long getCoinbaseValue() {
        if (this.coinbaseValue != -1) {
            return this.coinbaseValue;
        }
        return transactions.get(0).getTransactionsOut().get(0).getAmount();
    }

    public void setCoinbaseValue(long coinbaseValue) {
        this.coinbaseValue = coinbaseValue;
    }

    public int getVersion() {
        return header.getVersion();
    }

    public void setVersion(int version) {
        header.setVersion(version);
    }

    public String getPreviousBlockHash() {
        return header.getPreviousBlockHash();
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        header.setPreviousBlockHash(previousBlockHash);
    }

    public long getTimestamp() {
        return header.getTimestamp();
    }

    public void setTimestamp(long timestamp) {
        header.setTimestamp(timestamp);
    }

    public String getBits() {
        return header.getBits();
    }

    public void setBits(String bits) {
        header.setBits(bits);
    }

    public long getHeight() {
        return header.getHeight();
    }

    public void setHeight(long height) {
        header.setHeight(height);
    }

    public byte[] getData() throws DecoderException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getSize());
        byteBuffer.put(header.getData(BlockUtils.getMerkleRootFromTransactions(transactions)));
        byteBuffer.put(swapEndianness(nonce));
        byteBuffer.put(swapEndianness(mixHash));
        ByteUtils.writeVariableLong(transactions.size(), byteBuffer);
        for (Transaction transaction : transactions) {
            byteBuffer.put(transaction.getData());
        }
        return byteBuffer.flip().array();
    }

    public String getHashHexadecimal() throws DecoderException {
        return header.getHashHexadecimal(BlockUtils.getMerkleRootFromTransactions(transactions));
    }

    public String getNonce() {
        return nonce;
    }

    public String getMixHash() {
        return mixHash;
    }

    public void setMixHash(String mixHash) {
        this.mixHash = mixHash;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public int getSize() {
        int transactionsSize = transactions.stream().mapToInt(value -> value.getSize(true)).sum();
        return header.getSize() + 8 + 32 + 1 + transactionsSize;
    }

    public String getSeedHash() {
        byte[] bytes = new byte[32];
        Arrays.fill(bytes, (byte) 0);

        for (long i = 0; i < header.getHeight() / BLOCK_PER_EPOCH; i++) {
            bytes = DigestUtils.sha3_256(bytes);
        }

        return Hex.encodeHexString(bytes);
    }

    @Override
    public Block clone() {
        try {
            Block block = new Block();
            block.setData(this.getData());
            return block;
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BlockHeader getHeader() {
        return header;
    }

    public String getHeaderDataHexadecimal() throws DecoderException {
        return header.getDataHexadecimal(BlockUtils.getMerkleRootFromTransactions(transactions));
    }

    public String getHeaderHashHexadecimal() throws DecoderException {
        return header.getHashHexadecimal(BlockUtils.getMerkleRootFromTransactions(transactions));
    }

    public void setHeader(BlockHeader header) {
        this.header = header;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Block{" +
                "header=" + header +
                ", nonce='" + nonce + '\'' +
                ", mixHash='" + mixHash + '\'' +
                ", transactions=" + transactions +
                ", coinbaseValue=" + coinbaseValue +
                '}';
    }
}

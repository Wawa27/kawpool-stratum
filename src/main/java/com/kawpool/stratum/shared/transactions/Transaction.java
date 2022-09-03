package com.kawpool.stratum.shared.transactions;

import com.kawpool.stratum.shared.blocks.utils.BlockUtils;
import com.kawpool.stratum.shared.utils.ByteUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.kawpool.stratum.shared.utils.ByteUtils.readUnsignedIntegerLittleEndian;
import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndiannessHexadecimal;

public class Transaction {
    private int version;
    private String id;
    private List<TransactionIn> transactionsIn;
    private List<TransactionOut> transactionsOut;
    private Integer lockTime;
    private boolean useWitness;
    private int witnessFlag;
    private List<List<String>> witnessScripts;

    public Transaction() {
        this(null, new ArrayList<>(), new ArrayList<>());
    }

    public Transaction(List<TransactionIn> transactionsIn, List<TransactionOut> transactionsOut) {
        this(null, transactionsIn, transactionsOut);
    }

    public Transaction(String id, List<TransactionIn> transactionsIn, List<TransactionOut> transactionsOut) {
        this.transactionsIn = transactionsIn;
        this.transactionsOut = transactionsOut;
        this.version = 2;
        this.lockTime = 0;
        this.id = id;
        this.useWitness = false;
        this.witnessScripts = new ArrayList<>();
    }

    public void setData(ByteBuffer byteBuffer) {
        this.setVersion(ByteUtils.readIntegerLittleEndian(byteBuffer));

        long transactionInCount = ByteUtils.readVariableLong(byteBuffer);
        // BIP_0144 flag
        if (transactionInCount == 0) {
            this.useWitness = true;
            witnessFlag = byteBuffer.get();
            transactionInCount = ByteUtils.readVariableLong(byteBuffer);
        }
        for (int i = 0; i < transactionInCount; i++) {
            TransactionIn transactionIn = new TransactionIn();
            transactionIn.setData(byteBuffer);
            transactionsIn.add(transactionIn);
        }

        long transactionOutCount = ByteUtils.readVariableLong(byteBuffer);
        for (int i = 0; i < transactionOutCount; i++) {
            TransactionOut transactionOut = new TransactionOut();
            transactionOut.setData(byteBuffer);
            transactionsOut.add(transactionOut);
        }

        if (useWitness) {
            for (int i = 0; i < transactionInCount; i++) {
                ArrayList<String> witnesses = new ArrayList<>();
                long stackCount = ByteUtils.readVariableLong(byteBuffer);
                for (int j = 0; j < stackCount; j++) {
                    witnesses.add(ByteUtils.readString(byteBuffer, ByteUtils.readVariableLong(byteBuffer)));
                }
                witnessScripts.add(witnesses);
            }
        }

        this.lockTime = readUnsignedIntegerLittleEndian(byteBuffer);
    }

    public byte[] getData() throws DecoderException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getSize(this.useWitness));
        ByteUtils.writeIntegerLittleEndian(byteBuffer, version);
        if (useWitness) {
            byteBuffer.put((byte) 0).put((byte) 1);
        }
        ByteUtils.writeVariableLong(transactionsIn.size(), byteBuffer);
        for (TransactionIn transactionIn : transactionsIn) {
            byteBuffer.put(transactionIn.getData());
        }
        ByteUtils.writeVariableLong(transactionsOut.size(), byteBuffer);
        for (TransactionOut transactionOut : transactionsOut) {
            byteBuffer.put(transactionOut.getData());
        }
        if (useWitness) {
            for (List<String> witness : witnessScripts) {
                ByteUtils.writeVariableLong(witness.size(), byteBuffer);
                for (String stack : witness) {
                    ByteUtils.writeVariableLong(stack.length() / 2, byteBuffer);
                    byteBuffer.put(Hex.decodeHex(stack));
                }
            }
        }
        byteBuffer.put(Hex.decodeHex(TransactionUtils.getIntegerHexadecimal(lockTime)));
        return byteBuffer.flip().array();
    }

    public int getSize(boolean useSegregatedWitness) {
        int insSize = transactionsIn.stream().mapToInt(TransactionIn::getSize).sum();
        int outsSize = transactionsOut.stream().mapToInt(TransactionOut::getSize).sum();
        int segregatedWitnessSize = useWitness && useSegregatedWitness ?
                2 + witnessScripts.stream().mapToInt(Transaction::getWitnessSize).sum()
                : 0;
        return 4 + ByteUtils.getVariableLongSize(transactionsIn.size()) + insSize
                + ByteUtils.getVariableLongSize(transactionsOut.size()) + outsSize
                + segregatedWitnessSize
                + 4;
    }

    private static int getWitnessSize(List<String> witnesses) {
        return witnesses
                .stream()
                .mapToInt(stack -> ByteUtils.getVariableLongSize(stack.length() / 2) + stack.length() / 2)
                .sum()
                + ByteUtils.getVariableLongSize(witnesses.size());
    }

    public String getId() throws DecoderException {
        if (id != null) {
            return id;
        }
        return swapEndiannessHexadecimal(Hex.encodeHexString(BlockUtils.doubleSha256(getData())));
    }

    public void setUseWitness(boolean hasWitness) {
        this.useWitness = hasWitness;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TransactionIn> getTransactionsIn() {
        return transactionsIn;
    }

    public void setTransactionsIn(List<TransactionIn> transactionsIn) {
        this.transactionsIn = transactionsIn;
    }

    public List<TransactionOut> getTransactionsOut() {
        return transactionsOut;
    }

    public void setTransactionsOut(List<TransactionOut> transactionsOut) {
        this.transactionsOut = transactionsOut;
    }

    public Integer getLockTime() {
        return lockTime;
    }

    public void setLockTime(Integer lockTime) {
        this.lockTime = lockTime;
    }
}

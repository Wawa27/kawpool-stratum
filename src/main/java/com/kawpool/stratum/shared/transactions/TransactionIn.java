package com.kawpool.stratum.shared.transactions;

import com.kawpool.stratum.shared.utils.ByteUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

import static com.kawpool.stratum.shared.utils.ByteUtils.readString;
import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndiannessHexadecimal;

public class TransactionIn {
    private String previousHash;
    private String index;
    private String script;
    private String sequence;

    public TransactionIn() {
        this(
                "0000000000000000000000000000000000000000000000000000000000000000",
                "FFFFFFFF",
                "",
                "FFFFFFFF"
        );
    }

    /**
     * Creates a coinbase input with the given height and extra
     *
     * @param height
     */
    public TransactionIn(long height, int minerId) {
        this(
                "0000000000000000000000000000000000000000000000000000000000000000",
                "FFFFFFFF",
                swapEndiannessHexadecimal(String.format("%1$08X", height)) + swapEndiannessHexadecimal(String.format("%1$04X", minerId)),
                "FFFFFFFF"
        );
    }

    public TransactionIn(String previousHash, String index, String script, String sequence) {
        this.previousHash = previousHash;
        this.index = index;
        this.script = script;
        this.sequence = sequence;
    }

    public void setData(ByteBuffer data) {
        this.setPreviousHash(readString(data, 32));
        this.setIndex(readString(data, 4));
        Long size = ByteUtils.readVariableLong(data);
        this.setScript(readString(data, size));
        this.setSequence(readString(data, 4));
    }

    public byte[] getData() throws DecoderException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getSize())
                .put(Hex.decodeHex(previousHash))
                .put(Hex.decodeHex(index));
        ByteUtils.writeVariableLong(script.length() / 2, byteBuffer);
        return byteBuffer.put(Hex.decodeHex(script))
                .put(Hex.decodeHex(sequence))
                .flip().array();
    }

    public int getSize() {
        return 32 + 4 + ByteUtils.getVariableLongSize(script.length() / 2) + script.length() / 2 + 4;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}

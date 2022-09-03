package com.kawpool.stratum.shared.blocks.models;

import com.kawpool.stratum.shared.utils.ByteUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

import static com.kawpool.stratum.shared.blocks.utils.BlockUtils.doubleSha256;
import static com.kawpool.stratum.shared.utils.ByteUtils.*;
import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndianness;

/**
 * The com.com.kawpool.stratum.block headers contains all the data needed to mine a com.com.kawpool.stratum.block
 */
public class BlockHeader {
    private int version;
    private String previousBlockHash;
    private long timestamp;
    private String bits;
    private long height;

    public BlockHeader() {

    }

    public BlockHeader(int version, String previousBlockHash, long timestamp, String bits, long height) {
        this.version = version;
        this.previousBlockHash = previousBlockHash;
        this.timestamp = timestamp;
        this.bits = bits;
        this.height = height;
    }

    public void setData(ByteBuffer data) {
        this.version = readIntegerLittleEndian(data);
        this.previousBlockHash = readStringLittleEndian(data, 32);
        String merkleRootHash = readStringLittleEndian(data, 32);
        this.setTimestamp(readIntegerLittleEndian(data));
        this.bits = readStringLittleEndian(data, 4);
        this.height = readIntegerLittleEndian(data);
    }

    public byte[] getData(String merkleRootHash) throws DecoderException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(getSize());
        ByteUtils.writeIntegerLittleEndian(byteBuffer, version);
        byteBuffer.put(swapEndianness(previousBlockHash));
        byteBuffer.put(swapEndianness(merkleRootHash));
        byteBuffer.put(swapEndianness(String.format("%1$08X", timestamp)));
        byteBuffer.put(swapEndianness(bits));
        byteBuffer.put(swapEndianness(String.format("%1$08X", height)));

        return byteBuffer.flip().array();
    }

    public String getDataHexadecimal(String merkleRootHash) throws DecoderException {
        return Hex.encodeHexString(getData(merkleRootHash));
    }

    public String getHashHexadecimal(String merkleRootHash) throws DecoderException {
        return swapBytesEndiannessHexadecimal(doubleSha256(getData(merkleRootHash)));
    }

    public int getSize() {
        return 80;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}

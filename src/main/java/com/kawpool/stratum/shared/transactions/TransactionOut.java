package com.kawpool.stratum.shared.transactions;

import com.kawpool.stratum.shared.utils.ByteUtils;
import io.ipfs.multibase.Base58;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

import static com.kawpool.stratum.shared.utils.ByteUtils.readString;
import static com.kawpool.stratum.shared.utils.ByteUtils.readUnsignedLongLittleEndian;

public class TransactionOut {
    private Long amount;
    private String script;

    public TransactionOut() {
        this.script = "";
        this.amount = 0L;
    }

    public TransactionOut(String address, long amount) {
        byte[] data = ByteBuffer.allocate(25)
                .put((byte) 0x76).put((byte) 0xa9).put((byte) 0x14)
                .put(getBase58Address(address))
                .put((byte) 0x88)
                .put((byte) 0xac)
                .flip().array();
        this.script = Hex.encodeHexString(data);
        this.amount = amount;
    }

    public void setData(ByteBuffer data) {
        this.setAmount(readUnsignedLongLittleEndian(data));
        this.setScript(readString(data, ByteUtils.readVariableLong(data)));
    }

    public byte[] getData() throws DecoderException {
        ByteBuffer data = ByteBuffer.allocate(getSize());

        data.put(Hex.decodeHex(TransactionUtils.getLongHexadecimal(amount)));
        ByteUtils.writeVariableLong((script.length() / 2), data);
        data.put(Hex.decodeHex(script));

        return data.flip().array();
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getSize() {
        return 8 + ByteUtils.getVariableLongSize(script.length() / 2) + script.length() / 2;
    }

    public byte[] getBase58Address(String address) {
        byte[] base58Address = Base58.decode(address);
        return ArrayUtils.subarray(base58Address, 1, base58Address.length - 4);
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}

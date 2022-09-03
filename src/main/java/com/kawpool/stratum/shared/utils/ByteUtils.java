package com.kawpool.stratum.shared.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndianness;
import static com.kawpool.stratum.shared.utils.HexadecimalUtils.swapEndiannessHexadecimal;

public class ByteUtils {

    public static int readIntegerLittleEndian(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[4];
        byteBuffer.get(bytes, 0, bytes.length);
        return Integer.parseInt(swapEndiannessHexadecimal(Hex.encodeHexString(bytes)), 16);
    }

    public static int readInteger(ByteBuffer byteBuffer) {
        return byteBuffer.getInt();
    }

    public static ByteBuffer writeIntegerLittleEndian(ByteBuffer byteBuffer, int value) throws DecoderException {
        return byteBuffer.put(swapEndianness(String.format("%1$08X", value)));
    }

    public static ByteBuffer writeInteger(ByteBuffer byteBuffer, int value) throws DecoderException {
        return byteBuffer.put(Hex.decodeHex(String.format("%1$08X", value)));
    }

    public static Integer readUnsignedIntegerLittleEndian(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[4];
        byteBuffer.get(bytes, 0, bytes.length);
        return Integer.parseUnsignedInt(swapEndiannessHexadecimal(Hex.encodeHexString(bytes)), 16);
    }

    public static long readLongLittleEndian(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[8];
        byteBuffer.get(bytes, 0, bytes.length);
        return Long.parseLong(swapEndiannessHexadecimal(Hex.encodeHexString(bytes)), 16);
    }

    public static Long readUnsignedLong(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[8];
        byteBuffer.get(bytes, 0, bytes.length);
        return Long.parseUnsignedLong(Hex.encodeHexString(bytes), 16);
    }

    public static Long readUnsignedLongLittleEndian(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[8];
        byteBuffer.get(bytes, 0, bytes.length);
        return Long.parseUnsignedLong(swapEndiannessHexadecimal(Hex.encodeHexString(bytes)), 16);
    }

    public static String readStringLittleEndian(ByteBuffer byteBuffer, int size) {
        return swapEndiannessHexadecimal(readString(byteBuffer, size));
    }

    public static String readString(ByteBuffer byteBuffer, long size) {
        byte[] bytes = new byte[(int) size];
        byteBuffer.get(bytes, 0, bytes.length);
        return Hex.encodeHexString(bytes);
    }

    public static Long readVariableLong(ByteBuffer byteBuffer) {
        long value = Byte.toUnsignedInt(byteBuffer.get());

        if (value < 253) {
            return value;
        }
        if (value == 253) {
            return Byte.toUnsignedInt(byteBuffer.get())
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L;
        } else if (value == 254) {
            return Byte.toUnsignedInt(byteBuffer.get())
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L * 256L;
        } else {
            return Byte.toUnsignedInt(byteBuffer.get())
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L * 256L * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L * 256L * 256L * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L * 256L * 256L * 256L * 256L
                    + Byte.toUnsignedInt(byteBuffer.get()) * 256L * 256L * 256L * 256L * 256L * 256L * 256L;
        }
    }

    public static ByteBuffer writeVariableLong(long value, ByteBuffer byteBuffer) throws DecoderException {
        if (value < 253) {
            return byteBuffer.put(Hex.decodeHex(String.format("%1$02X", value)));
        } else if (value < 65535) {
            return byteBuffer.put((byte) 253)
                    .put((byte) (value % 256))
                    .put((byte) (value / 256));
        } else if (value < 4294967295L) {
            return byteBuffer.put((byte) 254)
                    .put((byte) (value % 256))
                    .put((byte) (value / 256))
                    .put((byte) (value / 256 / 256))
                    .put((byte) (value / 256 / 256 / 256));
        } else {
            return byteBuffer.put((byte) 255)
                    .put((byte) (value % 256))
                    .put((byte) (value / 256))
                    .put((byte) (value / 256 / 256))
                    .put((byte) (value / 256 / 256 / 256))
                    .put((byte) (value / 256 / 256 / 256 / 256))
                    .put((byte) (value / 256 / 256 / 256 / 256 / 256))
                    .put((byte) (value / 256 / 256 / 256 / 256 / 256 / 256))
                    .put((byte) (value / 256 / 256 / 256 / 256 / 256 / 256 / 256));
        }
    }

    public static int getVariableLongSize(long value) {
        if (value < 253) {
            return 1;
        } else if (value < 65535) {
            return 3;
        } else if (value < 4294967295L) {
            return 5;
        } else {
            return 9;
        }
    }

    public static byte[] swapBytesEndianness(byte[] bytes) throws DecoderException {
        return Hex.decodeHex(swapEndiannessHexadecimal(Hex.encodeHexString(bytes)));
    }

    public static String swapBytesEndiannessHexadecimal(byte[] bytes) {
        return swapEndiannessHexadecimal(Hex.encodeHexString(bytes));
    }
}

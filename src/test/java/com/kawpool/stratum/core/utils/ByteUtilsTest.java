package com.kawpool.stratum.core.utils;

import com.kawpool.stratum.shared.utils.ByteUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static com.kawpool.stratum.shared.utils.ByteUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteUtilsTest {

    @Test
    public void testReadInteger() throws DecoderException {
        ByteBuffer oneByteValue = ByteBuffer.wrap(Hex.decodeHex("00000003"));

        assertEquals(3, readInteger(oneByteValue));
    }

    @Test
    public void testReadIntegerLittleEndian() throws DecoderException {
        ByteBuffer oneByteValue = ByteBuffer.wrap(Hex.decodeHex("03000000"));

        assertEquals(3, readIntegerLittleEndian(oneByteValue));
    }

    @Test
    public void testReadOneByteVariableInteger() throws DecoderException {
        ByteBuffer oneByteValue = ByteBuffer.wrap(Hex.decodeHex("BB"));

        assertEquals(187, readVariableLong(oneByteValue));
    }

    @Test
    public void testReadTwoBytesVariableInteger() throws DecoderException {
        ByteBuffer twoBytesValue1 = ByteBuffer.wrap(Hex.decodeHex("FD00FF"));
        ByteBuffer twoBytesValue2 = ByteBuffer.wrap(Hex.decodeHex("FD3419"));
        ByteBuffer twoBytesValue3 = ByteBuffer.wrap(Hex.decodeHex("FDFD00"));

        assertEquals(65_280, readVariableLong(twoBytesValue1));
        assertEquals(6_452, readVariableLong(twoBytesValue2));
        assertEquals(253, readVariableLong(twoBytesValue3));
    }

    @Test
    public void testWriteTwoBytesVariableInteger() throws DecoderException {
        ByteBuffer twoBytesValue = ByteBuffer.allocate(3);

        ByteUtils.writeVariableLong(253L, twoBytesValue).flip();

        assertEquals("fdfd00", Hex.encodeHexString(twoBytesValue));
    }

    @Test
    public void testReadThreeBytesVariableInteger() throws DecoderException {
        ByteBuffer threeBytesValue1 = ByteBuffer.wrap(Hex.decodeHex("FE00DC4591"));
        ByteBuffer threeBytesValue2 = ByteBuffer.wrap(Hex.decodeHex("FE080081E5"));

        assertEquals(2_437_274_624L, readVariableLong(threeBytesValue1));
        assertEquals(3_850_436_616L, readVariableLong(threeBytesValue2));
    }

    @Test
    public void testReadFourBytesVariableInteger() throws DecoderException {
        ByteBuffer fourBytesValue1 = ByteBuffer.wrap(Hex.decodeHex("FF0000B4DA564E2857"));
        ByteBuffer fourBytesValue2 = ByteBuffer.wrap(Hex.decodeHex("FF4BF583A17D59C158"));

        assertEquals(6_280_355_815_311_540_224L, readVariableLong(fourBytesValue1));
        assertEquals(6_395_491_341_958_378_827L, readVariableLong(fourBytesValue2));
    }
}

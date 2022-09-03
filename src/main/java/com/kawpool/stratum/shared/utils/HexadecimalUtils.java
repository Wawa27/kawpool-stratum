package com.kawpool.stratum.shared.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HexadecimalUtils {

    public static byte[] swapEndianness(String hexadecimalString) throws DecoderException {
        return Hex.decodeHex(swapEndiannessHexadecimal(hexadecimalString));
    }

    public static String swapEndiannessHexadecimal(String hexadecimalString) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = hexadecimalString.length() - 1; i > 0; i -= 2) {
            stringBuilder.append(hexadecimalString.charAt(i - 1));
            stringBuilder.append(hexadecimalString.charAt(i - 0));
        }
        return stringBuilder.toString();
    }
}

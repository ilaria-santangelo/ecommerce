package com.ecommerceapp.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEncryption {

    public static final String CYPHER_TEXT_1 =
            "0110110001011100101100000101101011011110011011011100011111110001010000101000111000" +
            "0000110101011111001001001010110011110010001011111100110011011111101011011011010101" +
            "0010101010001100101101011101111100111100100111000101110111010011100011100101101101" +
            "0110000001001111101000010101100110111111111101111011110111101110010101110110101110" +
            "1001010101100011010010010011110101111010010001101010001001110100100101110101101110" +
            "0110101100101111110010101001011000010011100001011111100101010001110111001000001110" +
            "00001010101111001111";

    public static final String CYPHER_TEXT_2 =
            "0011110001111100110110011101110100110110000010001011111011100000110111011011001001" +
            "1100011101100011000110001010110100110101001111010001000010010110110010101110010101" +
            "1100001100111000110111100101111001100001000010111100111111111000001011101010100000" +
            "1100100001001110110111011001001110011000101000110100010110001111000010001010010100" +
            "1000100100100110100000111000101111000001011000110001110010101000111011111101111101" +
            "0000011001111111000001000101001111111011101101111111110111011011000001000111000011" +
            "0101011111111011110000011000011000101110001100100100100000000100001100100100011001" +
            "10";

    public static final String KEY = "0001001100110100010101110111100110011011101111001101111111110001";

    public static final String VECTOR = "0101010101010101010101010101010101010101010101010101010101010101";

    @Test
    public void testECB() {
        String decipher = Encryption.decryptECB(CYPHER_TEXT_1, KEY);
        String cypher = Encryption.encryptECB(decipher, KEY);
        Assertions.assertEquals(CYPHER_TEXT_1, cypher);
    }

    @Test
    public void testCBC() {
        String decipher = Encryption.decryptCBC(CYPHER_TEXT_2, KEY, VECTOR);
        String cypher = Encryption.encryptCBC(decipher, KEY, VECTOR);
        Assertions.assertEquals(CYPHER_TEXT_2, cypher);
    }
}
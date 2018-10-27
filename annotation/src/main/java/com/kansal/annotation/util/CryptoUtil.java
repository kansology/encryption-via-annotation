package com.kansal.annotation.util;

import com.kansal.annotation.enums.CipherVersion;
import com.kansal.annotation.model.KeyVector;
import com.kansal.annotation.service.AESCryptoService;
import com.kansal.annotation.service.ICryptoService;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CryptoUtil {

    private static Map<Integer, KeyVector> keys = new HashMap<>();
    private static String defaultVersion = "V1";
    private static String charsetName = "UTF-8";

    static {
        keys.put(1, KeyVector.builder()
                .iv("RandomInitVector")
                .key("Bar12345Bar12345")
                .build());
        keys.put(2, KeyVector.builder()
                .iv("RandomInitVector")
                .key("ssshhhhhhhhhhh!!!!")
                .build());
    }

    public static String encrypt(String plainText) throws UnsupportedEncodingException {
        int randomNum = ThreadLocalRandom.current().nextInt(1, keys.size() + 1);
        System.out.println("Fetching key:" + randomNum);
        KeyVector keyVector = keys.get(randomNum);

        CipherVersion cipherVersion = CipherVersion.valueOf(defaultVersion);

        ICryptoService cryptoService = getCipher(cipherVersion, keyVector.getIv().getBytes(charsetName),
                keyVector.getKey().getBytes(charsetName));
        String encryptedText = cryptoService.encrypt(plainText);

        return doPad(randomNum, cipherVersion.name(), encryptedText);
    }

    public static String decrypt(String encryptedText) throws UnsupportedEncodingException {
        String[] encryptedDetails = undoPad(encryptedText);
        System.out.println("encryptedDetails = " + encryptedDetails);

        KeyVector keyVector = keys.get(Integer.valueOf(encryptedDetails[0]));

        CipherVersion cipherVersion = CipherVersion.valueOf(encryptedDetails[1]);

        ICryptoService cryptoService = getCipher(cipherVersion, keyVector.getIv().getBytes(charsetName),
                keyVector.getKey().getBytes(charsetName));

        return cryptoService.decrypt(encryptedDetails[2]);
    }

    private static String doPad(Integer keyName, String cipherVersion, String encryptedText) throws UnsupportedEncodingException {
        System.out.println("keyName = [" + keyName + "], cipherVersion = [" + cipherVersion + "], encryptedText = [" + encryptedText + "]");
        String toEncode = String.format("%d|%s|%s", keyName, cipherVersion, encryptedText);
        return Base64.getEncoder().encodeToString(toEncode.getBytes(charsetName));
    }

    private static String[] undoPad(String encodedText) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText.getBytes(charsetName));
        String decodedText = new String(decodedBytes, charsetName);
        return decodedText.split("\\|");
    }

    public static ICryptoService getCipher(CipherVersion cipherVersion, byte[] iv, byte[] secretKey) {
        switch (cipherVersion) {
            case V1:
                return new AESCryptoService(iv, secretKey);
            case V2:
            case V3:
            default:
                throw new RuntimeException("Algorithm is not implemented for Cipher Type:" + cipherVersion);
        }
    }
}

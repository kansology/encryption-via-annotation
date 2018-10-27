package com.kansal.annotation.service;

import org.springframework.util.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AESCryptoService implements ICryptoService {

    private static final int DEFAULT_KEY_ITERATIONS = 2048;
    private static String ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static String UTF8 = "UTF-8";
    private byte[] iv;
    private int keySize = 256;
    private SecretKeySpec secret;

    /**
     * @param key
     * @param salt
     * @param keyIterations
     */
    public AESCryptoService(String key, String salt, Integer keyIterations) {
        try {
            if (StringUtils.isEmpty(key)) {
                throw new RuntimeException("The key field cannot be null ");
            }
            if (StringUtils.isEmpty(salt)) {
                throw new RuntimeException("The salt field cannot be null ");
            }
            if (keyIterations == null || keyIterations == 0) {
                keyIterations = DEFAULT_KEY_ITERATIONS;
            }
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), keyIterations, keySize);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            this.secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public AESCryptoService(byte[] iv, byte[] secretKey) {
        this.iv = iv;
        this.secret = new SecretKeySpec(secretKey, "AES");
    }


    /**
     * Encrypts the plain text and returns the encrypted text
     *
     * @param text
     * @return
     */
    public String encrypt(String text) {
        if (text == null) return null;
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secret, ivspec);
            byte[] encryptedTextBytes = cipher.doFinal(text.getBytes(UTF8));
            return Base64.getEncoder().encodeToString(encryptedTextBytes);

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException |
                BadPaddingException |
                NoSuchAlgorithmException |
                NoSuchPaddingException |
                UnsupportedEncodingException invalidKeyException) {
            invalidKeyException.printStackTrace();
            throw new RuntimeException(invalidKeyException);
        }
    }

    /**
     * @param encryptedText
     * @return
     */
    public String decrypt(String encryptedText) {
        if (encryptedText == null) return null;
        byte[] packageBytes = Base64.getDecoder().decode(encryptedText);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            return new String(cipher.doFinal(packageBytes));
        } catch (InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

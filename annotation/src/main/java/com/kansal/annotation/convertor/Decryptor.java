package com.kansal.annotation.convertor;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.kansal.annotation.util.CryptoUtil;
import org.springframework.stereotype.Component;

@Component
public class Decryptor extends StdConverter<String, String> {

    @Override
    public String convert(String encryptedText) {
        System.out.println("encryptedText: " + encryptedText);
        String decryptedValue;
        try {
            decryptedValue = CryptoUtil.decrypt(encryptedText);
        } catch (Exception e) {
            System.out.println("Exception while decrypting = " + e);
            return encryptedText;
        }
        return decryptedValue;
    }
}

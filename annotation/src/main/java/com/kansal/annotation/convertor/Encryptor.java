package com.kansal.annotation.convertor;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.kansal.annotation.util.CryptoUtil;
import org.springframework.stereotype.Component;

@Component
public class Encryptor extends StdConverter<String, String> {

    @Override
    public String convert(String plainText) {
        System.out.println("plainText: " + plainText);
        String encryptedValue;
        try {
            encryptedValue = CryptoUtil.encrypt(plainText);
        } catch (Exception e) {
            System.out.println("Exception while encrypting " + e);
            return plainText;
        }
        return encryptedValue;
    }
}

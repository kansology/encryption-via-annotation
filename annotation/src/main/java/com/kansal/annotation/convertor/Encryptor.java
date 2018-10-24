package com.kansal.annotation.convertor;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.kansal.annotation.service.CryptoService;

import java.io.UnsupportedEncodingException;

public class Encryptor extends StdConverter<String, String> {
    @Override
    public String convert(String value) {
        System.out.println("value: " + value);
        CryptoService cryptoService;
        String encryptedValue = value;
        try {
            cryptoService = new CryptoService("RandomInitVector".getBytes("UTF-8"),
                    "Bar12345Bar12345".getBytes("UTF-8"));
            encryptedValue = cryptoService.encrypt(value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptedValue;
    }
}

package com.kansal.annotation.convertor;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.kansal.annotation.service.CryptoService;

import java.io.UnsupportedEncodingException;

public class Decryptor extends StdConverter<String, String> {
    @Override
    public String convert(String value) {
        System.out.println("value: " + value);
        CryptoService cryptoService;
        String decryptedValue = value;
        try {
            cryptoService = new CryptoService("RandomInitVector".getBytes("UTF-8"),
                    "Bar12345Bar12345".getBytes("UTF-8"));
            decryptedValue = cryptoService.decrypt(value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decryptedValue;
    }
}

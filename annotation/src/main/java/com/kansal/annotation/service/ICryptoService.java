package com.kansal.annotation.service;

public interface ICryptoService {
    String encrypt(String plainText);
    String decrypt(String encryptedText);
}

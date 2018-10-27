package com.kansal.annotation.enums;

public enum CipherVersion {

    V1("AES", "AES-256 Implementation"),
    V2("RSA", "RSA Implementation"),
    V3("DES", "DES Implementation");

    private String description;
    private String cipherType;

    CipherVersion(String cipherType, String description) {
        this.description = description;
        this.cipherType = cipherType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCipherType() {
        return cipherType;
    }

    public void setCipherType(String cipherType) {
        this.cipherType = cipherType;
    }
}
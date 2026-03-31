package com.finance.config;

import java.security.MessageDigest;

public class CryptoUtils {

    public static String encryptSHA256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            byte[] byteData = md.digest();
            
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Encryption Error", e);
        }
    }
}
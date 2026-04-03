package com.finance.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ElectronicSignatureService {

    private final JdbcTemplate jdbcTemplate;

    public ElectronicSignatureService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String generateSignatureHash(String customerId, String documentId) {
        try {
            String rawData = customerId + documentId + LocalDateTime.now().toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSignature(String applicationId, String customerId, String signatureHash) {
        String sql = "INSERT INTO E_SIGNATURE (APPLICATION_ID, CUSTOMER_ID, SIGN_HASH, SIGN_DATE) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, applicationId, customerId, signatureHash, LocalDateTime.now());
    }
}
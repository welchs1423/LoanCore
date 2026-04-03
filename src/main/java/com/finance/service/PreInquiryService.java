package com.finance.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PreInquiryService {

    private final JdbcTemplate jdbcTemplate;

    public PreInquiryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void recordInquiry(String customerId, int creditScore, double estimatedRate, BigDecimal estimatedLimit) {
        String sql = "INSERT INTO PRE_INQUIRY_HISTORY (CUSTOMER_ID, CREDIT_SCORE, ESTIMATED_RATE, ESTIMATED_LIMIT, INQUIRY_DATE) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customerId, creditScore, estimatedRate, estimatedLimit, LocalDateTime.now());
    }
}
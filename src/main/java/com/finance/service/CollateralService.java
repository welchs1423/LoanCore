package com.finance.service;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollateralService {

    private final JdbcTemplate jdbcTemplate;

    public CollateralService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerCollateral(String applicationId, String collateralType, BigDecimal collateralValue) {
        String sql = "INSERT INTO COLLATERAL_INFO (APPLICATION_ID, COLLATERAL_TYPE, COLLATERAL_VALUE) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, applicationId, collateralType, collateralValue);
    }
}
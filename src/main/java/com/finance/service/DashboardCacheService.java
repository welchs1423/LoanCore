package com.finance.service;

import java.math.BigDecimal;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DashboardCacheService {

    @Cacheable(value = "dailyStats", key = "#date")
    public BigDecimal getDailyTotalLoanAmount(String date) {
        return new BigDecimal("500000000");
    }
}
package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DashboardCacheServiceTest {

    private DashboardCacheService dashboardCacheService;

    @BeforeEach
    public void setUp() {
        dashboardCacheService = new DashboardCacheService();
    }

    @Test
    public void testGetDailyTotalLoanAmount() {
        BigDecimal total = dashboardCacheService.getDailyTotalLoanAmount("2026-04-05");
        assertEquals(new BigDecimal("500000000"), total);
    }
}
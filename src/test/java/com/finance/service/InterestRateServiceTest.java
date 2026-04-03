package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterestRateServiceTest {

    private InterestRateService interestRateService;

    @BeforeEach
    public void setUp() {
        interestRateService = new InterestRateService();
    }

    @Test
    public void testCalculateApplicableRate() {
        assertEquals(3.5, interestRateService.calculateApplicableRate(950));
        assertEquals(5.5, interestRateService.calculateApplicableRate(750));
        assertEquals(9.9, interestRateService.calculateApplicableRate(500));
    }

    @Test
    public void testCalculateMaximumLimit() {
        assertEquals(new BigDecimal("100000000"), interestRateService.calculateMaximumLimit(900));
        assertEquals(new BigDecimal("30000000"), interestRateService.calculateMaximumLimit(700));
        assertEquals(BigDecimal.ZERO, interestRateService.calculateMaximumLimit(550));
    }
}
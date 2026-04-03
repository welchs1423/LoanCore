package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EarlyRepaymentServiceTest {

    private EarlyRepaymentService earlyRepaymentService;

    @BeforeEach
    public void setUp() {
        earlyRepaymentService = new EarlyRepaymentService();
    }

    @Test
    public void testCalculateEarlyRepaymentFee() {
        BigDecimal remainingPrincipal = new BigDecimal("10000000");
        int remainingDays = 180;
        int totalLoanDays = 365;

        BigDecimal fee = earlyRepaymentService.calculateEarlyRepaymentFee(remainingPrincipal, remainingDays, totalLoanDays);
        
        assertEquals(new BigDecimal("73973"), fee);
    }
}
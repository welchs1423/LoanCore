package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartialRepaymentServiceTest {

    private PartialRepaymentService partialRepaymentService;

    @BeforeEach
    public void setUp() {
        partialRepaymentService = new PartialRepaymentService();
    }

    @Test
    public void testProcessPartialRepayment() {
        BigDecimal currentBalance = new BigDecimal("10000000");
        BigDecimal repaymentAmount = new BigDecimal("1000000");
        BigDecimal annualRate = new BigDecimal("0.365"); 
        int elapsedDays = 30;

        BigDecimal newBalance = partialRepaymentService.processPartialRepayment(currentBalance, repaymentAmount, annualRate, elapsedDays);
        
        assertEquals(new BigDecimal("9300000"), newBalance);
    }
}
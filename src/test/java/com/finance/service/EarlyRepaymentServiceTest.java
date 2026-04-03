package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class EarlyRepaymentServiceTest {

    @Test
    public void testCalculateFee() {
        EarlyRepaymentService service = new EarlyRepaymentService();
        BigDecimal amount = new BigDecimal("10000000");
        
        BigDecimal fee = service.calculateFee(amount, 365, 1095);
        
        assertEquals(new BigDecimal("50000"), fee);
    }
}
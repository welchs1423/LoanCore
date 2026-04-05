package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OpenBankingServiceTest {

    private OpenBankingService openBankingService;

    @BeforeEach
    public void setUp() {
        openBankingService = new OpenBankingService();
    }

    @Test
    public void testCheckAccountBalance() {
        BigDecimal balance = openBankingService.checkAccountBalance("004", "1234567890");
        assertEquals(new BigDecimal("1500000"), balance);
    }
}
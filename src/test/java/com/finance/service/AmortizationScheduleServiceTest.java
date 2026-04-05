package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmortizationScheduleServiceTest {

    private AmortizationScheduleService amortizationScheduleService;

    @BeforeEach
    public void setUp() {
        amortizationScheduleService = new AmortizationScheduleService();
    }

    @Test
    public void testGenerateSchedule() {
        BigDecimal principal = new BigDecimal("10000000");
        BigDecimal annualRate = new BigDecimal("0.05");
        int months = 12;

        List<BigDecimal> schedule = amortizationScheduleService.generateSchedule(principal, annualRate, months);
        
        assertFalse(schedule.isEmpty());
        assertEquals(12, schedule.size());
    }
}
package com.finance.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmlMonitoringBatchServiceTest {

    private AmlMonitoringBatchService amlMonitoringBatchService;

    @BeforeEach
    public void setUp() {
        amlMonitoringBatchService = new AmlMonitoringBatchService();
    }

    @Test
    public void testDetectSuspiciousTransactions() {
        amlMonitoringBatchService.detectSuspiciousTransactions();
    }
}
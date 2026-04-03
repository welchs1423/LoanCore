package com.finance.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutoDebitBatchServiceTest {

    private AutoDebitBatchService autoDebitBatchService;

    @BeforeEach
    public void setUp() {
        autoDebitBatchService = new AutoDebitBatchService();
    }

    @Test
    public void testProcessAutoDebit() {
        autoDebitBatchService.processAutoDebit();
    }
}
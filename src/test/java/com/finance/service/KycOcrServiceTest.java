package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KycOcrServiceTest {

    private KycOcrService kycOcrService;

    @BeforeEach
    public void setUp() {
        kycOcrService = new KycOcrService();
    }

    @Test
    public void testExtractResidentNumber() {
        String result = kycOcrService.extractResidentNumber("/path/to/id_card.jpg");
        assertEquals("900101-1234567", result);
    }

    @Test
    public void testVerifyIdentity() {
        boolean isValid = kycOcrService.verifyIdentity("900101-1234567", "900101-1234567");
        assertTrue(isValid);
    }
}
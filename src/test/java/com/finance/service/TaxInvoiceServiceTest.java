package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxInvoiceServiceTest {

    private TaxInvoiceService taxInvoiceService;

    @BeforeEach
    public void setUp() {
        taxInvoiceService = new TaxInvoiceService();
    }

    @Test
    public void testIssueInvoice() {
        String invoiceId = taxInvoiceService.issueInvoice("123-45-67890", new BigDecimal("55000"));
        assertNotNull(invoiceId);
        assertTrue(invoiceId.startsWith("INV_123-45-67890_"));
    }
}
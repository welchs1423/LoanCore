package com.finance.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class TaxInvoiceService {

    public String issueInvoice(String businessNumber, BigDecimal amount) {
        return "INV_" + businessNumber + "_" + System.currentTimeMillis();
    }
}
package com.finance.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class TaxDataService {

    public Map<String, Object> aggregateYearlyInterest(String customerId, int year) {
        Map<String, Object> result = new HashMap<>();
        result.put("customerId", customerId);
        result.put("year", year);
        result.put("totalInterestPaid", new BigDecimal("1250000"));
        return result;
    }
}
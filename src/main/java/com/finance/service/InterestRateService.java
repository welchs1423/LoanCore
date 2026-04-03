package com.finance.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class InterestRateService {

    public double calculateApplicableRate(int creditScore) {
        if (creditScore >= 900) return 3.5;
        if (creditScore >= 800) return 4.2;
        if (creditScore >= 700) return 5.5;
        if (creditScore >= 600) return 7.0;
        return 9.9;
    }

    public BigDecimal calculateMaximumLimit(int creditScore) {
        if (creditScore >= 900) return new BigDecimal("100000000");
        if (creditScore >= 800) return new BigDecimal("50000000");
        if (creditScore >= 700) return new BigDecimal("30000000");
        if (creditScore >= 600) return new BigDecimal("10000000");
        return BigDecimal.ZERO;
    }
}
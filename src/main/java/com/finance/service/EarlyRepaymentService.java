package com.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class EarlyRepaymentService {

    public BigDecimal calculateEarlyRepaymentFee(BigDecimal remainingPrincipal, int remainingDays, int totalLoanDays) {
        if (remainingDays <= 0 || totalLoanDays <= 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal feeRate = new BigDecimal("0.015");
        BigDecimal daysRatio = BigDecimal.valueOf((double) remainingDays / totalLoanDays);
        
        return remainingPrincipal.multiply(feeRate).multiply(daysRatio).setScale(0, RoundingMode.HALF_UP);
    }
}
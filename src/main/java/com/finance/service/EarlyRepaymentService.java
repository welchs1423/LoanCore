package com.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class EarlyRepaymentService {

    public BigDecimal calculateFee(BigDecimal repaymentAmount, int remainingDays, int totalLoanDays) {
        BigDecimal feeRate = new BigDecimal("0.015");
        
        if (remainingDays <= 0 || totalLoanDays <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal daysRatio = new BigDecimal(remainingDays).divide(new BigDecimal(totalLoanDays), 4, RoundingMode.HALF_UP);
        return repaymentAmount.multiply(feeRate).multiply(daysRatio).setScale(0, RoundingMode.DOWN);
    }
}
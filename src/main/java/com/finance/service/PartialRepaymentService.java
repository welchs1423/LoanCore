package com.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartialRepaymentService {

    @Transactional
    public BigDecimal processPartialRepayment(BigDecimal currentBalance, BigDecimal repaymentAmount, BigDecimal annualRate, int elapsedDays) {
        if (repaymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return currentBalance;
        }

        BigDecimal dailyRate = annualRate.divide(new BigDecimal("365"), 6, RoundingMode.HALF_UP);
        BigDecimal accruedInterest = currentBalance.multiply(dailyRate).multiply(new BigDecimal(elapsedDays)).setScale(0, RoundingMode.DOWN);
        
        BigDecimal principalRepayment = repaymentAmount.subtract(accruedInterest);

        if (principalRepayment.compareTo(BigDecimal.ZERO) > 0) {
            return currentBalance.subtract(principalRepayment);
        }
        
        return currentBalance;
    }
}
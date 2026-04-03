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

        // 정밀도 손실(소수점 잘림)을 방지하기 위해 곱셈을 먼저 하고 나눗셈을 가장 마지막에 수행합니다.
        BigDecimal numerator = repaymentAmount.multiply(feeRate).multiply(new BigDecimal(remainingDays));
        return numerator.divide(new BigDecimal(totalLoanDays), 0, RoundingMode.DOWN);
    }
}
package com.finance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AmortizationScheduleService {
    
    // 원리금균등상환 스케줄 계산
    public List<BigDecimal> generateSchedule(BigDecimal principal, BigDecimal annualRate, int months) {
        List<BigDecimal> schedule = new ArrayList<>();
        
        if (annualRate.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal monthlyPrincipal = principal.divide(new BigDecimal(months), 0, RoundingMode.DOWN);
            for (int i = 0; i < months; i++) {
                schedule.add(monthlyPrincipal);
            }
            return schedule;
        }

        BigDecimal monthlyRate = annualRate.divide(new BigDecimal("12"), 6, RoundingMode.HALF_UP);
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal ratePower = onePlusRate.pow(months);
        
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(ratePower);
        BigDecimal denominator = ratePower.subtract(BigDecimal.ONE);
        
        BigDecimal monthlyPayment = numerator.divide(denominator, 0, RoundingMode.DOWN);

        for (int i = 0; i < months; i++) {
            schedule.add(monthlyPayment);
        }
        return schedule;
    }
}
package com.finance.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepaymentService {

    public List<Map<String, Object>> calculateSchedule(BigDecimal amount, double annualRate, int months) {
        List<Map<String, Object>> schedule = new ArrayList<>();
        double monthlyRate = annualRate / 12 / 100;
        BigDecimal monthlyRateBd = BigDecimal.valueOf(monthlyRate);
        
        BigDecimal monthlyPayment = amount.multiply(monthlyRateBd)
            .divide(BigDecimal.ONE.subtract(BigDecimal.ONE.add(monthlyRateBd).pow(-months)), 0, RoundingMode.HALF_UP);

        BigDecimal balance = amount;
        for (int i = 1; i <= months; i++) {
            BigDecimal interest = balance.multiply(monthlyRateBd).setScale(0, RoundingMode.HALF_UP);
            BigDecimal principal = monthlyPayment.subtract(interest);
            balance = balance.subtract(principal);

            Map<String, Object> row = new HashMap<>();
            row.put("month", i);
            row.put("payment", monthlyPayment);
            row.put("principal", principal);
            row.put("interest", interest);
            row.put("balance", balance.max(BigDecimal.ZERO));
            schedule.add(row);
        }
        return schedule;
    }
}
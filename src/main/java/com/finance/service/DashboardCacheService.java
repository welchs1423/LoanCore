package com.finance.service;

import java.math.BigDecimal;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.finance.mapper.LoanMapper;

@Service
public class DashboardCacheService {

    private final LoanMapper loanMapper;

    public DashboardCacheService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Cacheable(value = "dailyStats", key = "#date")
    public BigDecimal getDailyTotalLoanAmount(String date) {
        return loanMapper.getTotalLoanAmountByDate(date);
    }
}
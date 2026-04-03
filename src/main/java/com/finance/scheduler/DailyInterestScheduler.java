package com.finance.scheduler;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Component
public class DailyInterestScheduler {

    private final LoanMapper loanMapper;

    public DailyInterestScheduler(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Scheduled(cron = "0 30 2 * * ?")
    public void accrueDailyInterest() {
        List<LoanApplication> activeLoans = loanMapper.searchApplicationsDynamic(null, "ACTIVE", null, null);
        BigDecimal dailyRate = new BigDecimal("0.0001");
        
        for (LoanApplication loan : activeLoans) {
            BigDecimal dailyInterest = loan.getAmount().multiply(dailyRate);
            System.out.println("대출번호: " + loan.getApplicationId() + " 발생이자: " + dailyInterest);
        }
    }
}
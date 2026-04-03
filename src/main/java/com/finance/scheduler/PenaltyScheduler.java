package com.finance.scheduler;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Component
public class PenaltyScheduler {

    private final LoanMapper loanMapper;

    public PenaltyScheduler(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void applyOverduePenalty() {
        List<LoanApplication> overdueLoans = loanMapper.searchApplicationsDynamic(null, "OVERDUE", null, null);
        BigDecimal dailyPenaltyRate = new BigDecimal("0.0005"); 

        for (LoanApplication loan : overdueLoans) {
            BigDecimal penalty = loan.getAmount().multiply(dailyPenaltyRate);
            loan.setAmount(loan.getAmount().add(penalty));
            loanMapper.updateApplication(loan);
        }
    }
}
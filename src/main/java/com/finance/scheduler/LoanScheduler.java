package com.finance.scheduler;

import com.finance.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoanScheduler {

    @Autowired
    private LoanMapper loanMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    public void expirePendingLoans() {
        loanMapper.updateApplicationStatusBulk("EXPIRED", null);
    }
}
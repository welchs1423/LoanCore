package com.finance.scheduler;

import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;
import com.finance.service.ExternalCreditApiClient;

@Component
public class EarlyWarningScheduler {

    private final LoanMapper loanMapper;
    private final ExternalCreditApiClient creditApiClient;

    public EarlyWarningScheduler(LoanMapper loanMapper, ExternalCreditApiClient creditApiClient) {
        this.loanMapper = loanMapper;
        this.creditApiClient = creditApiClient;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void monitorCreditDrops() {
        List<LoanApplication> activeLoans = loanMapper.searchApplicationsDynamic(null, "APPROVED", null, null);
        for (LoanApplication loan : activeLoans) {
            int currentScore = creditApiClient.fetchCreditScore(loan.getCustomerId());
            if (currentScore < 600) {
                System.out.println(loan.getCustomerId() + " " + currentScore);
            }
        }
    }
}
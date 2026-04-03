package com.finance.scheduler;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class OverpaymentBatchService {

    private final LoanMapper loanMapper;

    public OverpaymentBatchService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void scanOverpayments() {
        List<LoanApplication> closedLoans = loanMapper.searchApplicationsDynamic(null, "CLOSED", null, null);
        
        for (LoanApplication loan : closedLoans) {
            if (loan.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                BigDecimal refundAmount = loan.getAmount().abs();
                System.out.println("환불대상고객: " + loan.getCustomerId() + " 환불금액: " + refundAmount);
            }
        }
    }
}
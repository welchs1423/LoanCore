package com.finance.scheduler;

import java.time.LocalDate;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutoDebitBatchService {

    @Scheduled(cron = "0 0 9 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void processAutoDebit() {
        LocalDate today = LocalDate.now();
        List<String> targetAccounts = getDueAccounts(today);
        
        for (String account : targetAccounts) {
            requestCmsTransfer(account);
        }
    }

    private List<String> getDueAccounts(LocalDate date) {
        return List.of("110-123-456789", "04-000-1111");
    }

    private void requestCmsTransfer(String accountNumber) {
        // CMS 출금 청구 전문 발송 로직
    }
}
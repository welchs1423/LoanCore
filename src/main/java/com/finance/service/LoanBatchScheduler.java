package com.finance.service;

import com.finance.domain.LoanApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LoanBatchScheduler {

    @Autowired
    private LoanReviewService reviewService;

    @Scheduled(cron = "0 * * * * *")
    public void monitorPendingApplications() {
        List<LoanApplication> pendingList = reviewService.searchApplications(null, "PENDING");
        
        System.out.println("========================================");
        System.out.println("[BATCH SYSTEM] 심사 대기(PENDING) 모니터링");
        System.out.println("현재 처리 대기 중인 신청 건수: " + pendingList.size() + "건");
        System.out.println("========================================");
    }
}
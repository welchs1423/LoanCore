package com.finance.service;

import com.finance.domain.LoanApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanBatchScheduler {

    private static final Logger logger = LoggerFactory.getLogger(LoanBatchScheduler.class);

    @Autowired
    private LoanReviewService reviewService;

    // 매 1분마다 PENDING 상태인 대출 건수 체크
    @Scheduled(cron = "0 * * * * *")
    public void monitorPendingLoans() {
        // 파라미터 4개로 수정 (startDate, endDate에 null 전달)
        List<LoanApplication> pendingList = reviewService.searchApplications(null, "PENDING", null, null);
        if (!pendingList.isEmpty()) {
            logger.warn("[모니터링 알림] 현재 심사 대기 중인 대출 건수가 {}건 있습니다. 신속한 처리가 필요합니다.", pendingList.size());
        } else {
            logger.info("[모니터링 알림] 심사 대기 중인 건이 없습니다.");
        }
    }
}
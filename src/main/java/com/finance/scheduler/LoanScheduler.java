package com.finance.scheduler;

import com.finance.mapper.LoanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoanScheduler {

    private static final Logger logger = LoggerFactory.getLogger(LoanScheduler.class);

    @Autowired
    private LoanMapper loanMapper;

    @Scheduled(cron = "*/10 * * * * *")
    public void monitorPendingLoans() {
        int pendingCount = 0;
        logger.info("[Scheduler] 심사 대기(PENDING) 건수 모니터링 실행 중... (현재 대기 건수: {})", pendingCount);
    }
}
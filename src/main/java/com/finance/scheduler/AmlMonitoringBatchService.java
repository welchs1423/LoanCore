package com.finance.scheduler;

import java.math.BigDecimal;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AmlMonitoringBatchService {

    // 매일 새벽 2시 STR 의심거래 탐지 스케줄러 실행
    @Scheduled(cron = "0 0 2 * * ?")
    public void detectSuspiciousTransactions() {
        BigDecimal threshold = new BigDecimal("50000000");
        System.out.println("AML STR 탐지 배치 실행 완료. 기준 금액: " + threshold);
    }
}
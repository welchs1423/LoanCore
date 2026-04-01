package com.finance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Async("asyncExecutor")
    public void sendStatusChangeNotification(String customerId, String status) {
        try {
            logger.info("[비동기 작업 시작] 외부 API 통신: 대상 {}, 상태 {}", customerId, status);
            Thread.sleep(3000); 
            logger.info("[비동기 작업 완료] 알림 전송 성공: 대상 {}", customerId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("알림 발송 작업 중단", e);
        }
    }
}
package com.finance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Async("asyncExecutor")
    public void sendStatusChangeNotification(String customerId, String status, String traceId) {
        // 부모 스레드의 traceId를 자식 스레드 MDC에 설정
        if (traceId != null) MDC.put("traceId", traceId);
        
        try {
            logger.info("[비동기 알림] 전송 시작 - 대상: {}, 상태: {}", customerId, status);
            Thread.sleep(3000); 
            logger.info("[비동기 알림] 전송 완료");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            MDC.clear();
        }
    }
}
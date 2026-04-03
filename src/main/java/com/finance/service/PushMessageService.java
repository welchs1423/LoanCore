package com.finance.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PushMessageService {

    private final JdbcTemplate jdbcTemplate;

    public PushMessageService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 특정 사용자에게 푸시 알림을 발송하고 이력을 저장합니다.
     */
    public String sendPushNotification(String userId, String title, String body) {
        // 실제 구현 시에는 Firebase Admin SDK를 사용하여 FCM 토큰으로 발송합니다.
        String messageId = "PUSH_" + UUID.randomUUID().toString().substring(0, 8);
        
        System.out.println("Push Sent to [" + userId + "]: " + title + " - " + body);

        // 발송 이력 DB 기록
        String sql = "INSERT INTO PUSH_HISTORY (MESSAGE_ID, USER_ID, TITLE, BODY, SEND_DATE) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, messageId, userId, title, body, LocalDateTime.now());

        return messageId;
    }
}
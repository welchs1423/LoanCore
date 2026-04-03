package com.finance.service;

import org.junit.jupiter.api.Test;

public class NotificationServiceTest {

    @Test
    public void testSendAlimTalk() {
        NotificationService service = new NotificationService();
        service.sendAlimTalk("010-1234-5678", "대출 승인이 완료되었습니다.");
    }
}
package com.finance.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendAlimTalk(String phoneNumber, String message) {
        System.out.println("알림톡발송대상: " + phoneNumber + " 메시지: " + message);
    }
}
package com.finance.service;

import org.springframework.stereotype.Service;

@Service
public class CreditBureauClient {
    
    // 외부 CB사 신용점수 조회 모의 로직
    public int fetchCreditScore(String residentNumber) {
        if (residentNumber == null || residentNumber.trim().isEmpty()) {
            return 0;
        }
        return 850;
    }
}
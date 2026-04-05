package com.finance.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class OpenBankingService {
    
    // 오픈뱅킹 타행 계좌 잔액 조회 모의 로직
    public BigDecimal checkAccountBalance(String bankCode, String accountNumber) {
        if (bankCode == null || accountNumber == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("1500000");
    }
}
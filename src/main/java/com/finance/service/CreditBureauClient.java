package com.finance.service;

import org.springframework.stereotype.Service;
import com.finance.mapper.LoanMapper;

@Service
public class CreditBureauClient {

    private final LoanMapper loanMapper;

    public CreditBureauClient(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    public int fetchCreditScore(String residentNumber) {
        if (residentNumber == null || residentNumber.trim().isEmpty()) {
            return 0;
        }
        
        Integer score = loanMapper.getMockCreditScore(residentNumber);
        return score != null ? score : 0;
    }
}
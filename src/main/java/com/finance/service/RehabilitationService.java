package com.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class RehabilitationService {

    private final LoanMapper loanMapper;

    public RehabilitationService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void registerBankruptcy(String applicationId) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        
        if (app != null && ("ACTIVE".equals(app.getStatusCode()) || "OVERDUE".equals(app.getStatusCode()))) {
            // 파산/회생 신청 시 대출 상태를 변경하여 이자 부과 스케줄러에서 제외되도록 처리
            app.setStatusCode("BANKRUPT");
            loanMapper.updateApplication(app);
            System.out.println("대출번호 [" + applicationId + "] 개인회생 접수 완료. 이자 부과 정지.");
        }
    }
}
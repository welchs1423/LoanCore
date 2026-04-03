package com.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class NplService {

    private final LoanMapper loanMapper;

    public NplService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void processWriteOff(String applicationId) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null && "OVERDUE".equals(app.getStatusCode())) {
            app.setStatusCode("WRITTEN_OFF");
            loanMapper.updateApplication(app);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void processSaleToAgency(String applicationId, String agencyName) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null && ("OVERDUE".equals(app.getStatusCode()) || "WRITTEN_OFF".equals(app.getStatusCode()))) {
            app.setStatusCode("SOLD_NPL");
            loanMapper.updateApplication(app);
            // 실제 환경에서는 agencyName 업체로 원장 데이터를 이관하는 로그를 남깁니다.
            System.out.println("채권 매각 완료: " + agencyName);
        }
    }
}
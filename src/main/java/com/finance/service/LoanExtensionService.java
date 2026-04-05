package com.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class LoanExtensionService {

    private final LoanMapper loanMapper;

    public LoanExtensionService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean extendMaturity(String applicationId, int extraMonths) {
        if (extraMonths <= 0) {
            return false;
        }

        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null && "ACTIVE".equals(app.getStatusCode())) {
            app.setStatusCode("EXTENDED");
            loanMapper.updateApplication(app);
            return true;
        }
        return false;
    }
}
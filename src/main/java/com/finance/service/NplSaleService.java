package com.finance.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class NplSaleService {

    private final LoanMapper loanMapper;

    public NplSaleService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean sellNpl(String applicationId, String agencyName, BigDecimal saleAmount) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        
        if (app != null && ("OVERDUE".equals(app.getStatusCode()) || "BANKRUPT".equals(app.getStatusCode()))) {
            app.setStatusCode("SOLD");
            loanMapper.updateApplication(app);
            return true;
        }
        return false;
    }
}
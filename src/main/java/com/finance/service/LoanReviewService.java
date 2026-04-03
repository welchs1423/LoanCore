package com.finance.service;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoanReviewService {

    @Autowired
    private LoanMapper loanMapper;

    @Transactional(rollbackFor = Exception.class)
    public void applyLoan(LoanApplication app) {
        if (app.getApplicationId() == null || app.getApplicationId().isEmpty()) {
            app.setApplicationId("L" + System.currentTimeMillis());
        }
        loanMapper.insertApplication(app);
    }

    @Cacheable(value = "loanCache", key = "'allLoans'")
    public List<LoanApplication> getAllApplications() {
        return loanMapper.selectAllApplications();
    }

    public LoanApplication getApplicationById(String id) {
        return loanMapper.selectApplicationById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void approveLoan(String id) {
        LoanApplication app = loanMapper.selectApplicationById(id);
        if (app != null) {
            app.setStatusCode("APPROVED");
            loanMapper.updateApplication(app);
        }
    }
}
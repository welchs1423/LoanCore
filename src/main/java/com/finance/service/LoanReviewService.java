package com.finance.service;

import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class LoanReviewService {

    @Autowired
    private LoanMapper loanMapper;

    public String reviewLoan(LoanApplication app) {
        String resultMessage;
        
        if (app.getAmount().compareTo(new BigDecimal("100000000")) > 0) {
            app.setStatusCode("REJECT");
            resultMessage = "거절: 1억원 초과 대출은 지점 방문이 필요합니다.";
        } else {
            app.setStatusCode("APPROVE");
            resultMessage = "승인: 기표 대기 상태로 변경되었습니다.";
        }
        
        loanMapper.insertApplication(app);
        
        return resultMessage;
    }

    @Transactional(readOnly = true)
    public List<LoanApplication> getAllApplications() {
        return loanMapper.selectAllApplications();
    }

    @Transactional(readOnly = true)
    public LoanApplication getApplicationById(String id) {
        return loanMapper.selectApplicationById(id);
    }

    public void deleteApplication(String id) {
        loanMapper.deleteApplication(id);
    }

    public void updateApplication(String id, String customerId, BigDecimal amount) {
        LoanApplication app = new LoanApplication(id, customerId, amount);
        
        if (amount.compareTo(new BigDecimal("100000000")) > 0) {
            app.setStatusCode("REJECT");
        } else {
            app.setStatusCode("APPROVE");
        }
        
        loanMapper.updateApplication(app);
    }

    @Transactional(readOnly = true)
    public List<LoanApplication> searchApplications(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return loanMapper.selectAllApplications();
        }
        return loanMapper.searchApplicationsByCustomerId(keyword);
    }
}
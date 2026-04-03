package com.finance.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class RefinancingService {

    private final LoanMapper loanMapper;

    public RefinancingService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void executeRefinancing(LoanApplication newApp, String targetBank, BigDecimal targetAmount) {
        loanMapper.insertApplication(newApp);
        System.out.println(targetBank + " " + targetAmount + " ");
    }
}
package com.finance.service;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.finance.domain.LoanApplication;
import com.finance.mapper.LoanMapper;

@Service
public class CertificateService {

    private final LoanMapper loanMapper;

    public CertificateService(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    public String issueBalanceCertificate(String applicationId) {
        LoanApplication app = loanMapper.selectApplicationById(applicationId);
        if (app != null) {
            BigDecimal balance = app.getAmount();
            String certId = "CERT_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            System.out.println("발급번호: " + certId + " 잔액: " + balance);
            return certId;
        }
        return null;
    }
}
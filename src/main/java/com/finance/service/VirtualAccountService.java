package com.finance.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VirtualAccountService {

    private final JdbcTemplate jdbcTemplate;

    public VirtualAccountService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String issueVirtualAccount(String applicationId, String bankCode) {
        // 실제 펌뱅킹 연동 시 은행 API를 호출하여 번호를 받아옵니다. 여기서는 모의 생성합니다.
        String virtualAccountNumber = "V" + bankCode + System.currentTimeMillis();
        
        String sql = "INSERT INTO VIRTUAL_ACCOUNT (APPLICATION_ID, BANK_CODE, ACCOUNT_NUMBER, STATUS) VALUES (?, ?, ?, 'ACTIVE')";
        jdbcTemplate.update(sql, applicationId, bankCode, virtualAccountNumber);
        
        return virtualAccountNumber;
    }
}
package com.finance.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentManagementService {

    private final JdbcTemplate jdbcTemplate;

    public DocumentManagementService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerFileMeta(String applicationId, String fileType, String s3Url, long fileSize) {
        String sql = "INSERT INTO LOAN_DOCUMENTS (APPLICATION_ID, FILE_TYPE, FILE_URL, FILE_SIZE) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, applicationId, fileType, s3Url, fileSize);
    }
}
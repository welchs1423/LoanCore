package com.finance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class QnAService {

    private final JdbcTemplate jdbcTemplate;

    public QnAService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerQuestion(String customerId, String title, String content) {
        String sql = "INSERT INTO QNA_BOARD (CUSTOMER_ID, TITLE, CONTENT, STATUS, REG_DATE) VALUES (?, ?, ?, 'WAITING', ?)";
        jdbcTemplate.update(sql, customerId, title, content, LocalDateTime.now());
    }

    public void registerAnswer(Long questionId, String answerContent) {
        String sql = "UPDATE QNA_BOARD SET ANSWER_CONTENT = ?, STATUS = 'ANSWERED', ANS_DATE = ? WHERE ID = ?";
        jdbcTemplate.update(sql, answerContent, LocalDateTime.now(), questionId);
    }

    public List<Map<String, Object>> getCustomerQuestions(String customerId) {
        String sql = "SELECT * FROM QNA_BOARD WHERE CUSTOMER_ID = ? ORDER BY REG_DATE DESC";
        return jdbcTemplate.queryForList(sql, customerId);
    }
}
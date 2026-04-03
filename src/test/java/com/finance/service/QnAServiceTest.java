package com.finance.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class QnAServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private QnAService qnaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterQuestion() {
        qnaService.registerQuestion("CUST01", "대출 상환 문의", "상환 일자를 변경하고 싶습니다.");

        verify(jdbcTemplate).update(
            anyString(),
            eq("CUST01"),
            eq("대출 상환 문의"),
            eq("상환 일자를 변경하고 싶습니다."),
            any(LocalDateTime.class)
        );
    }

    @Test
    public void testRegisterAnswer() {
        qnaService.registerAnswer(1L, "고객센터로 연락 바랍니다.");

        verify(jdbcTemplate).update(
            anyString(),
            eq("고객센터로 연락 바랍니다."),
            any(LocalDateTime.class),
            eq(1L)
        );
    }
}
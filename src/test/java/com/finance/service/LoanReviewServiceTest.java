package com.finance.service;

import com.finance.domain.LoanApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
@Transactional
public class LoanReviewServiceTest {

    @Autowired
    private LoanReviewService reviewService;

    @Test
    public void testReviewLoan_Approve() {
        LoanApplication app = new LoanApplication("TEST-001", "CUST-TEST", new BigDecimal("50000000"));

        reviewService.reviewLoan(app);

        assertEquals("APPROVE", app.getStatusCode());
    }

    @Test
    public void testReviewLoan_Reject() {
        LoanApplication app = new LoanApplication("TEST-002", "CUST-TEST", new BigDecimal("150000000"));

        reviewService.reviewLoan(app);

        assertEquals("REJECT", app.getStatusCode());
    }
}
package com.finance.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.finance.domain.LoanApplication;
import com.finance.service.LoanReviewService;

public class CustomerWebControllerTest {

    @Mock
    private LoanReviewService loanReviewService;

    @InjectMocks
    private CustomerWebController customerWebController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMyPage() {
        String appId = "L001";
        LoanApplication app = new LoanApplication();
        Model model = new ExtendedModelMap();

        when(loanReviewService.getApplicationById(appId)).thenReturn(app);
        when(loanReviewService.generateSchedule(appId)).thenReturn(new ArrayList<>());

        String viewName = customerWebController.myPage(appId, model);

        assertEquals("customer/mypage", viewName);
        verify(loanReviewService).getApplicationById(appId);
        verify(loanReviewService).generateSchedule(appId);
    }
}
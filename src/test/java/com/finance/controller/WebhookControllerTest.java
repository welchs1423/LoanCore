package com.finance.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.finance.service.PaymentService;

public class WebhookControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private WebhookController webhookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleDepositCallback() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("applicationId", "L001");
        payload.put("amount", 5000);

        ResponseEntity<String> response = webhookController.handleDepositCallback(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody());
        verify(paymentService).processPayment("L001", new BigDecimal("5000"));
    }
}
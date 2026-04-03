package com.finance.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.service.PaymentService;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private final PaymentService paymentService;

    public WebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> handleDepositCallback(@RequestBody Map<String, Object> payload) {
        try {
            String applicationId = (String) payload.get("applicationId");
            BigDecimal amount = new BigDecimal(payload.get("amount").toString());

            paymentService.processPayment(applicationId, amount);

            return ResponseEntity.ok("SUCCESS");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("FAIL: " + e.getMessage());
        }
    }
}
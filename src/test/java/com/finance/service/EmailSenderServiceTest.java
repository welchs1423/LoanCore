package com.finance.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailSenderServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSenderService emailSenderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail() {
        emailSenderService.sendEmail("test@example.com", "Subject", "Body");
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}
package com.finance.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendApprovalEmail(String toAddress, File pdfAttachment) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(toAddress);
        helper.setSubject("대출 심사 결과 안내");
        helper.setText("대출 심사가 승인되었습니다. 첨부 문서를 확인 바랍니다.");
        
        if (pdfAttachment != null && pdfAttachment.exists()) {
            helper.addAttachment(pdfAttachment.getName(), pdfAttachment);
        }
        
        mailSender.send(message);
    }
}
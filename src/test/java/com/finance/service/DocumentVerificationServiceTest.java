package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentVerificationServiceTest {

    private DocumentVerificationService documentVerificationService;

    @BeforeEach
    public void setUp() {
        documentVerificationService = new DocumentVerificationService();
    }

    @Test
    public void testDetectForgery() throws Exception {
        File fakeDoc = new File("idcard_edited.jpg");
        File realDoc = new File("idcard_original.jpg");
        
        try {
            fakeDoc.createNewFile();
            realDoc.createNewFile();
            
            assertTrue(documentVerificationService.detectForgery(fakeDoc));
            assertFalse(documentVerificationService.detectForgery(realDoc));
        } finally {
            fakeDoc.delete();
            realDoc.delete();
        }
    }
}
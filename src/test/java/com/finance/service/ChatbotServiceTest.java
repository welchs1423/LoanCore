package com.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChatbotServiceTest {

    private ChatbotService chatbotService;

    @BeforeEach
    public void setUp() {
        chatbotService = new ChatbotService();
    }

    @Test
    public void testGetAnswer_KnownKeyword() {
        String answer = chatbotService.getAnswer("대출 금리가 어떻게 되나요?");
        assertTrue(answer.contains("최저 3.5%"));
    }

    @Test
    public void testGetAnswer_UnknownKeyword() {
        String answer = chatbotService.getAnswer("비밀번호를 까먹었어요");
        assertEquals("죄송합니다. 해당 질문은 상담원 연결이 필요합니다. (고객센터: 1588-0000)", answer);
    }
}
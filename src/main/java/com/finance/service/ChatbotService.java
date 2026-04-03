package com.finance.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    private final Map<String, String> responseDictionary;

    public ChatbotService() {
        responseDictionary = new HashMap<>();
        responseDictionary.put("금리", "고객님의 신용점수에 따라 최저 3.5%부터 차등 적용됩니다.");
        responseDictionary.put("상환", "원리금균등상환 방식으로 매월 지정된 결제일에 자동 출금됩니다.");
        responseDictionary.put("연체", "연체 시 연체 일수에 따라 가산 금리(0.05%)가 매일 부과됩니다.");
        responseDictionary.put("한도", "최대 대출 한도는 1억 원이며, 가조회를 통해 확인 가능합니다.");
    }

    public String getAnswer(String userMessage) {
        for (Map.Entry<String, String> entry : responseDictionary.entrySet()) {
            if (userMessage.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "죄송합니다. 해당 질문은 상담원 연결이 필요합니다. (고객센터: 1588-0000)";
    }
}
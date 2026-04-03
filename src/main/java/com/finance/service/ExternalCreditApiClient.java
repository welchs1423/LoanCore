package com.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalCreditApiClient {

    private final RestTemplate restTemplate;

    public ExternalCreditApiClient() {
        // 별도의 빈 등록 없이 내부 인스턴스로 사용하여 설정 오류를 방지합니다.
        this.restTemplate = new RestTemplate();
    }

    public int fetchCreditScore(String customerId) {
        try {
            // 실제 외부 연동 시 아래 주석 해제 및 API 엔드포인트 적용
            // String apiUrl = "https://api.external-credit-agency.com/score/" + customerId;
            // Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            // return (Integer) response.get("score");
            
            // 현재는 모의(Mock) 점수 반환
            return 850; 
        } catch (Exception e) {
            // 통신 실패 시 기본값 또는 예외 처리
            return 500;
        }
    }
}
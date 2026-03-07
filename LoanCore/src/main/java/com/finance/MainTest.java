package com.finance;

import com.finance.domain.LoanApplication;
import com.finance.service.LoanReviewService;
import java.math.BigDecimal;

public class MainTest {
    public static void main(String[] args) {
        
        System.out.println("=== LoanCore 비즈니스 로직 테스트 시작 ===\n");

        // 1. 대출 신청서 작성 (고객 C001이 6천만 원 신청)
        LoanApplication app1 = new LoanApplication("APP-1001", "C001", new BigDecimal("60000000"));
        
        // 2. 대출 신청서 작성 (고객 C002가 3천만 원 신청)
        LoanApplication app2 = new LoanApplication("APP-1002", "C002", new BigDecimal("30000000"));

        // 3. 심사 서비스 담당자(객체) 소환
        LoanReviewService reviewService = new LoanReviewService();

        // 4. 첫 번째 고객 심사 (거절 예상)
        System.out.println(app1.getApplicationInfo());
        String result1 = reviewService.reviewLoan(app1);
        System.out.println("-> 결과: " + result1);
        System.out.println("-> 상태 변경 확인: " + app1.getApplicationInfo() + "\n");

        // 5. 두 번째 고객 심사 (승인 예상)
        System.out.println(app2.getApplicationInfo());
        String result2 = reviewService.reviewLoan(app2);
        System.out.println("-> 결과: " + result2);
        System.out.println("-> 상태 변경 확인: " + app2.getApplicationInfo());
    }
}
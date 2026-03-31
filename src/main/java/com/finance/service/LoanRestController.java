package com.finance.service;

import com.finance.domain.LoanApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoanRestController {

    @Autowired
    private LoanReviewService reviewService;

    @GetMapping("/api/check-customer")
    public Map<String, Object> checkCustomer(@RequestParam("customerId") String customerId) {
        
        List<LoanApplication> existingApps = reviewService.searchApplications(customerId, null);
        
        boolean hasPending = false;
        for (LoanApplication app : existingApps) {
            if ("PENDING".equals(app.getStatusCode()) || "APPROVE".equals(app.getStatusCode())) {
                hasPending = true;
                break;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("exists", hasPending);
        response.put("message", hasPending ? "이미 진행 중이거나 승인된 대출 건이 존재합니다." : "신청 가능한 고객입니다.");

        return response;
    }
}
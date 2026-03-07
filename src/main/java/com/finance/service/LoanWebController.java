package com.finance.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.finance.domain.LoanApplication;

@Controller
public class LoanWebController {

    private LoanReviewService reviewService = new LoanReviewService();

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/test-loan", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String testLoan() {
        LoanApplication app = new LoanApplication("APP-2001", "CUST-WEB", new java.math.BigDecimal("30000000"));
        String result = reviewService.reviewLoan(app);
        return "<h3>심사 결과: " + result + "</h3><p>상태: " + app.getStatusCode() + "</p>";
    }
}
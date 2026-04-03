package com.finance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.domain.LoanApplication;
import com.finance.service.LoanReviewService;

@Controller
@RequestMapping("/customer")
public class CustomerWebController {

    private final LoanReviewService loanReviewService;

    public CustomerWebController(LoanReviewService loanReviewService) {
        this.loanReviewService = loanReviewService;
    }

    @GetMapping("/mypage")
    public String myPage(@RequestParam("id") String applicationId, Model model) {
        LoanApplication app = loanReviewService.getApplicationById(applicationId);
        List<Map<String, Object>> schedule = loanReviewService.generateSchedule(applicationId);
        
        model.addAttribute("loanInfo", app);
        model.addAttribute("repaymentSchedule", schedule);
        
        return "customer/mypage";
    }
}
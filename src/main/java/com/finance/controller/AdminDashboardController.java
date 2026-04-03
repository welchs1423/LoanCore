package com.finance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.finance.service.LoanReviewService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final LoanReviewService loanReviewService;

    public AdminDashboardController(LoanReviewService loanReviewService) {
        this.loanReviewService = loanReviewService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Map<String, Object>> stats = loanReviewService.getLoanStatistics();
        int totalCount = loanReviewService.getTotalCount();
        
        model.addAttribute("stats", stats);
        model.addAttribute("totalCount", totalCount);
        
        return "admin/dashboard";
    }
}
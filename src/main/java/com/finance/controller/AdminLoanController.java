package com.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.domain.LoanApplication;
import com.finance.service.LoanReviewService;

@Controller
@RequestMapping("/admin/loan")
public class AdminLoanController {

    private final LoanReviewService loanReviewService;

    public AdminLoanController(LoanReviewService loanReviewService) {
        this.loanReviewService = loanReviewService;
    }

    @GetMapping("/detail")
    public String loanDetail(@RequestParam("id") String applicationId, Model model) {
        LoanApplication app = loanReviewService.getApplicationById(applicationId);
        model.addAttribute("loan", app);
        return "admin/loanDetail";
    }
}
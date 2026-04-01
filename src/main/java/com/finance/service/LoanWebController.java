package com.finance.service;

import com.finance.domain.LoanApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
public class LoanWebController {

    @Autowired
    private LoanReviewService reviewService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalCount", reviewService.getTotalCount());
        model.addAttribute("applications", reviewService.getAllApplications());
        return "index";
    }

    @GetMapping("/apply")
    public String applyForm() {
        return "apply";
    }

    @PostMapping("/submit-loan")
    public String submitLoan(@RequestParam("customerId") String customerId,
                             @RequestParam("amount") BigDecimal amount,
                             @RequestParam("address") String address) {
        LoanApplication app = new LoanApplication();
        app.setCustomerId(customerId);
        app.setAmount(amount);
        app.setAddress(address);
        reviewService.applyLoan(app);
        return "redirect:/";
    }
}
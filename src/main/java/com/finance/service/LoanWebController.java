package com.finance.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.List;
import com.finance.domain.LoanApplication;

@Controller
public class LoanWebController {

    private LoanReviewService reviewService = new LoanReviewService();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/apply")
    public String showApplyForm() {
        return "apply";
    }

    @PostMapping("/submit-loan")
    public String submitLoan(@RequestParam("customerId") String customerId,
                             @RequestParam("amount") BigDecimal amount,
                             Model model) {

        LoanApplication app = new LoanApplication("APP-" + System.currentTimeMillis(), customerId, amount);
        String reviewResult = reviewService.reviewLoan(app);

        model.addAttribute("customerId", customerId);
        model.addAttribute("amount", amount);
        model.addAttribute("statusCode", app.getStatusCode());
        model.addAttribute("reviewMessage", reviewResult);

        return "result";
    }

    @GetMapping("/list")
    public String listApplications(Model model) {
        List<LoanApplication> list = reviewService.getAllApplications();
        model.addAttribute("loanList", list);
        return "list";
    }

    @GetMapping("/detail")
    public String detailApplication(@RequestParam("id") String id, Model model) {
        LoanApplication app = reviewService.getApplicationById(id);
        model.addAttribute("app", app);
        return "detail";
    }
}
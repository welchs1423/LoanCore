package com.finance.service;

import com.finance.domain.LoanApplication;
import com.finance.domain.LoanMemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String id, Model model) {
        LoanApplication app = reviewService.getApplicationById(id);
        model.addAttribute("app", app);
        return "detail";
    }

    @PostMapping("/approve")
    public String approveLoan(@RequestParam("applicationId") String applicationId) {
        LoanApplication app = reviewService.getApplicationById(applicationId);
        if (app != null) {
            reviewService.reviewLoan(app);
        }
        return "redirect:/";
    }

    @GetMapping("/api/memos/{appId}")
    @ResponseBody
    public List<LoanMemo> getMemos(@PathVariable("appId") String appId) {
        return reviewService.getMemosByAppId(appId);
    }

    @PostMapping("/api/memos")
    @ResponseBody
    public String addMemo(@RequestBody LoanMemo memo) {
        reviewService.addMemo(memo);
        return "success";
    }
}
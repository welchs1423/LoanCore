package com.finance.service;

import com.finance.domain.LoanApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class LoanWebController {

    @Autowired
    private LoanReviewService reviewService;

    @GetMapping("/")
    public String index(Model model) {
        List<LoanApplication> list = reviewService.getAllApplications();
        model.addAttribute("recentApps", list);
        model.addAttribute("totalCount", reviewService.getTotalCount());
        return "index";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "startDate", required = false) String startDate,
                       @RequestParam(value = "endDate", required = false) String endDate,
                       Model model) {
        List<LoanApplication> loanList = reviewService.searchApplications(keyword, status, startDate, endDate);
        model.addAttribute("loanList", loanList);
        model.addAttribute("totalPages", 1);
        model.addAttribute("currentPage", 1);
        return "list";
    }

    @GetMapping("/apply")
    public String applyForm() {
        return "apply";
    }

    @PostMapping("/submit-loan")
    public String submitLoan(@RequestParam("customerId") String customerId,
                             @RequestParam("amount") BigDecimal amount,
                             @RequestParam("address") String address,
                             @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) {
        LoanApplication app = new LoanApplication();
        app.setCustomerId(customerId);
        app.setAmount(amount);
        app.setAddress(address);

        if (uploadFile != null && !uploadFile.isEmpty()) {
            String uploadDir = "C:/upload/loan_docs/"; 
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String originalName = uploadFile.getOriginalFilename();
            String savedName = UUID.randomUUID().toString() + "_" + originalName;
            try {
                uploadFile.transferTo(new File(uploadDir + savedName));
                app.setFileName(savedName); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        reviewService.approveLoan(applicationId);
        return "redirect:/detail/" + applicationId;
    }

    @GetMapping("/audit")
    public String auditLogDashboard(Model model) {
        model.addAttribute("logs", reviewService.getRecentAuditLogs());
        return "audit";
    }
}
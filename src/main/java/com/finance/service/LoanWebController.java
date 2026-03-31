package com.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LoanReviewService reviewService;

    @GetMapping("/")
    public String index(Model model) {
        long totalCount = reviewService.getTotalCount();
        List<LoanApplication> allList = reviewService.getAllApplications();
        
        long approveCount = allList.stream().filter(app -> "APPROVE".equals(app.getStatusCode())).count();
        long rejectCount = allList.stream().filter(app -> "REJECT".equals(app.getStatusCode())).count();
        
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("approveCount", approveCount);
        model.addAttribute("rejectCount", rejectCount);
        
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
    public String listApplications(@RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "status", required = false) String status,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model) {
        int pageSize = 5;
        
        if ((keyword != null && !keyword.trim().isEmpty()) || (status != null && !status.trim().isEmpty())) {
            List<LoanApplication> list = reviewService.searchApplications(keyword, status);
            model.addAttribute("loanList", list);
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", status);
            return "list";
        }

        List<LoanApplication> list = reviewService.getApplicationsWithPaging(page, pageSize);
        int totalCount = reviewService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("loanList", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        
        return "list";
    }

    @GetMapping("/detail")
    public String detailApplication(@RequestParam("id") String id, Model model) {
        LoanApplication app = reviewService.getApplicationById(id);
        model.addAttribute("app", app);
        return "detail";
    }

    @PostMapping("/delete")
    public String deleteApplication(@RequestParam("id") String id) {
        reviewService.deleteApplication(id);
        return "redirect:/list";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") String id, Model model) {
        LoanApplication app = reviewService.getApplicationById(id);
        model.addAttribute("app", app);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateApplication(@RequestParam("id") String id,
                                    @RequestParam("customerId") String customerId,
                                    @RequestParam("amount") BigDecimal amount) {
        reviewService.updateApplication(id, customerId, amount);
        return "redirect:/detail?id=" + id;
    }
}
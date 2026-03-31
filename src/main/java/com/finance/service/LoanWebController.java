package com.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public String submitLoan(@Valid @ModelAttribute LoanApplication app,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "apply";
        }

        app.setApplicationId("APP-" + System.currentTimeMillis());
        String reviewResult = reviewService.reviewLoan(app);

        model.addAttribute("customerId", app.getCustomerId());
        model.addAttribute("amount", app.getAmount());
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
    
    @PostMapping("/submit-loan")
    public String submitLoan(@Valid @ModelAttribute LoanApplication app,
                             BindingResult bindingResult,
                             @RequestParam("uploadFile") MultipartFile uploadFile,
                             Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "apply";
        }

        // 파일 업로드 처리
        if (!uploadFile.isEmpty()) {
            String uploadFolder = "C:/upload_test/";
            File saveDir = new File(uploadFolder);
            if (!saveDir.exists()) saveDir.mkdirs(); // 폴더가 없으면 생성

            String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
            uploadFile.transferTo(new File(uploadFolder + fileName));
            app.setFileName(fileName); // DB 저장용 파일명 세팅
        }

        app.setApplicationId("APP-" + System.currentTimeMillis());
        reviewService.reviewLoan(app);

        model.addAttribute("customerId", app.getCustomerId());
        model.addAttribute("amount", app.getAmount());
        model.addAttribute("statusCode", app.getStatusCode());
        model.addAttribute("fileName", app.getFileName());

        return "result";
    }

    @GetMapping("/excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        List<LoanApplication> list = reviewService.getAllApplications();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("대출신청내역");
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("신청 번호");
        headerRow.createCell(1).setCellValue("고객 ID");
        headerRow.createCell(2).setCellValue("신청 금액(원)");
        headerRow.createCell(3).setCellValue("진행 상태");

        int rowNum = 1;
        for (LoanApplication app : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(app.getApplicationId());
            row.createCell(1).setCellValue(app.getCustomerId());
            row.createCell(2).setCellValue(app.getAmount().doubleValue());
            row.createCell(3).setCellValue(app.getStatusCode());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=loan_applications.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
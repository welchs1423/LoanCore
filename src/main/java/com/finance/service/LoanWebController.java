package com.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
                             @RequestParam("uploadFile") MultipartFile uploadFile,
                             Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "apply";
        }

        if (!uploadFile.isEmpty()) {
            String uploadFolder = "C:/upload_test/";
            File saveDir = new File(uploadFolder);
            if (!saveDir.exists()) saveDir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
            uploadFile.transferTo(new File(uploadFolder + fileName));
            app.setFileName(fileName);
        }

        app.setApplicationId("APP-" + System.currentTimeMillis());
        String reviewResult = reviewService.reviewLoan(app);

        model.addAttribute("customerId", app.getCustomerId());
        model.addAttribute("amount", app.getAmount());
        model.addAttribute("statusCode", app.getStatusCode());
        model.addAttribute("reviewMessage", reviewResult);
        model.addAttribute("fileName", app.getFileName());

        return "result";
    }

    @GetMapping("/list")
    public String listApplications(@RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "status", required = false) String status,
                                   @RequestParam(value = "startDate", required = false) String startDate,
                                   @RequestParam(value = "endDate", required = false) String endDate,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model) {
        int pageSize = 10; 
        
        boolean isSearch = (keyword != null && !keyword.trim().isEmpty()) || 
                           (status != null && !status.trim().isEmpty()) ||
                           (startDate != null && !startDate.trim().isEmpty()) ||
                           (endDate != null && !endDate.trim().isEmpty());

        if (isSearch) {
            List<LoanApplication> list = reviewService.searchApplications(keyword, status, startDate, endDate);
            model.addAttribute("loanList", list);
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", status);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            return "list";
        }

        List<LoanApplication> list = reviewService.getApplicationsWithPaging(page, pageSize);
        int totalCount = reviewService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("loanList", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages > 0 ? totalPages : 1);
        
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
        headerRow.createCell(4).setCellValue("주소");

        int rowNum = 1;
        for (LoanApplication app : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(app.getApplicationId());
            row.createCell(1).setCellValue(app.getCustomerId());
            row.createCell(2).setCellValue(app.getAmount().doubleValue());
            row.createCell(3).setCellValue(app.getStatusCode());
            row.createCell(4).setCellValue(app.getAddress() != null ? app.getAddress() : "");
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=loan_applications.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/excel/upload")
    public String uploadExcel(@RequestParam("excelFile") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/list";
        }
        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                LoanApplication app = new LoanApplication();
                app.setApplicationId("APP-EXCEL-" + System.currentTimeMillis() + "-" + i);
                
                if (row.getCell(0) != null) app.setCustomerId(row.getCell(0).getStringCellValue());
                if (row.getCell(1) != null) app.setAmount(BigDecimal.valueOf(row.getCell(1).getNumericCellValue()));
                if (row.getCell(2) != null) app.setAddress(row.getCell(2).getStringCellValue());
                
                if (app.getCustomerId() != null && app.getAmount() != null) {
                    reviewService.reviewLoan(app);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/list";
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        String uploadFolder = "C:/upload_test/";
        File file = new File(uploadFolder + fileName);
        
        if (!file.exists()) return;

        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        }
    }

    @PostMapping("/bulk-update")
    public String bulkUpdateStatus(@RequestParam("bulkStatus") String status, 
                                   @RequestParam(value = "appIds", required = false) List<String> appIds) {
        if (appIds != null && !appIds.isEmpty()) {
            reviewService.updateStatusBulk(status, appIds);
        }
        return "redirect:/list";
    }
}
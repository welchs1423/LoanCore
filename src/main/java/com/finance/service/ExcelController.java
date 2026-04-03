// 기존 com.finance.service.ExcelController.java 확인
package com.finance.service;

import com.finance.mapper.LoanMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
public class ExcelController {

    @Autowired
    private LoanMapper loanMapper;

    @GetMapping("/admin/excel/download")
    public void downloadExcel(HttpServletResponse response) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("대출신청내역");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("신청ID");
        headerRow.createCell(1).setCellValue("고객명");
        headerRow.createCell(2).setCellValue("신청금액");
        headerRow.createCell(3).setCellValue("상태");

        // LoanMapper의 전체 목록을 Map으로 가져오는 selectAllLoans 메서드 호출
        List<Map<String, Object>> loanList = loanMapper.selectAllLoans();
        
        int rowNum = 1;
        for (Map<String, Object> loan : loanList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(String.valueOf(loan.get("APPLICATION_ID")));
            row.createCell(1).setCellValue(String.valueOf(loan.get("CUSTOMER_NAME")));
            row.createCell(2).setCellValue(String.valueOf(loan.get("AMOUNT")));
            row.createCell(3).setCellValue(String.valueOf(loan.get("STATUS")));
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("대출심사내역.xlsx", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
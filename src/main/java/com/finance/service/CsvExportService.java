package com.finance.service;

import java.io.PrintWriter;
import java.util.List;
import org.springframework.stereotype.Service;
import com.finance.domain.LoanApplication;

@Service
public class CsvExportService {

    public void exportToCsv(PrintWriter writer, List<LoanApplication> loans) {
        writer.println("ApplicationID,CustomerID,Amount,Status");
        
        if (loans != null) {
            for (LoanApplication loan : loans) {
                writer.println(
                    loan.getApplicationId() + "," + 
                    loan.getCustomerId() + "," + 
                    loan.getAmount() + "," + 
                    loan.getStatusCode()
                );
            }
        }
    }
}
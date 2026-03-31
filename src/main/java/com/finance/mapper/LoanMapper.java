package com.finance.mapper;

import com.finance.domain.LoanApplication;
import java.util.List;

public interface LoanMapper {
    
    // Create
    void insertApplication(LoanApplication app);
    
    // Read
    List<LoanApplication> selectAllApplications();
    LoanApplication selectApplicationById(String id);
    List<LoanApplication> searchApplicationsByCustomerId(String customerId);
    
    // Update
    void updateApplication(LoanApplication app);
    
    // Delete
    void deleteApplication(String id);
}
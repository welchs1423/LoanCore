package com.finance.mapper;

import com.finance.domain.LoanApplication;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface LoanMapper {
    
    void insertApplication(LoanApplication app);
    
    List<LoanApplication> selectAllApplications();
    
    LoanApplication selectApplicationById(String id);
    
    List<LoanApplication> searchApplicationsDynamic(@Param("keyword") String keyword, @Param("status") String status);
    
    void updateApplication(LoanApplication app);
    
    void deleteApplication(String id);
    
    List<LoanApplication> selectApplicationsWithPaging(@Param("offset") int offset, @Param("limit") int limit);
    
    int countAllApplications();
}
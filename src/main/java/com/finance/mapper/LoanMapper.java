package com.finance.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.finance.domain.LoanApplication;

@Mapper
public interface LoanMapper {

    void insertApplication(LoanApplication application);
    
    LoanApplication selectApplicationById(String applicationId);
    
    void updateApplication(LoanApplication application);
    
    List<LoanApplication> selectAllApplications();
    
    List<Map<String, Object>> selectAllLoans();
    
    int countAllApplications();
    
    List<Map<String, Object>> getLoanStatistics();
    
    void updateApplicationStatusBulk(@Param("status") String status, @Param("ids") List<String> applicationIds);

    List<LoanApplication> searchApplicationsDynamic(
        @Param("customerId") String customerId, 
        @Param("statusCode") String statusCode, 
        @Param("startDate") String startDate, 
        @Param("endDate") String endDate
    );

    BigDecimal getTotalLoanAmountByDate(@Param("targetDate") String targetDate);
    
    Integer getMockCreditScore(@Param("residentNumber") String residentNumber);
}
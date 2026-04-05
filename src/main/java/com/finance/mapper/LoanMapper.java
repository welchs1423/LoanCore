package com.finance.mapper;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.finance.domain.LoanApplication;

@Mapper
public interface LoanMapper {
    LoanApplication selectApplicationById(String applicationId);
    void updateApplication(LoanApplication application);
    List<LoanApplication> searchApplicationsDynamic(
        @Param("customerId") String customerId, 
        @Param("statusCode") String statusCode, 
        @Param("startDate") String startDate, 
        @Param("endDate") String endDate
    );
    
    // 대시보드 통계용 일별 대출 총액 조회
    BigDecimal getTotalLoanAmountByDate(@Param("targetDate") String targetDate);
    
    // 모의 신용평가사 데이터 조회
    Integer getMockCreditScore(@Param("residentNumber") String residentNumber);
}
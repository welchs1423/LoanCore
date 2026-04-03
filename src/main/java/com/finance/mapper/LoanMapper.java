package com.finance.mapper;

import com.finance.domain.LoanApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface LoanMapper {
    void insertApplication(LoanApplication app);
    List<LoanApplication> selectAllApplications();
    LoanApplication selectApplicationById(String id);
    List<LoanApplication> searchApplicationsDynamic(@Param("keyword") String keyword, @Param("status") String status, @Param("startDate") String startDate, @Param("endDate") String endDate);
    void updateApplication(LoanApplication app);
    void deleteApplication(String id);
    List<LoanApplication> selectApplicationsWithPaging(@Param("limit") int limit, @Param("offset") int offset);
    int countAllApplications();
    void updateApplicationStatusBulk(@Param("status") String status, @Param("ids") List<String> ids);
    List<Map<String, Object>> selectAllLoans();
    List<Map<String, Object>> getLoanStatistics();
}
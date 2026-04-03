package com.finance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.finance.domain.LoanApplication;

@Mapper
public interface LoanMapper {

	int countAllApplications();

	List<LoanApplication> selectAllApplications();

	void insertApplication(LoanApplication app);

	LoanApplication selectApplicationById(String id);

	void deleteApplication(String id);

	void updateApplication(LoanApplication app);

	List<LoanApplication> searchApplicationsDynamic(@Param("keyword") String keyword, @Param("status") String status,
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	List<LoanApplication> selectApplicationsWithPaging(@Param("offset") int offset, @Param("pageSize") int pageSize);

	void updateApplicationStatusBulk(@Param("status") String status, @Param("appIds") List<String> appIds);

	List<Map<String, Object>> selectAllLoans();
}
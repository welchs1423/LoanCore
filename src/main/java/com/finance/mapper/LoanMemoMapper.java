package com.finance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.finance.domain.LoanMemo;

@Mapper
public interface LoanMemoMapper {

	List<LoanMemo> selectMemos(String applicationId);

	void insertMemo(LoanMemo memo);

	void deleteMemo(int memoId);
}
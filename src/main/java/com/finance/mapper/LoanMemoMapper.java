package com.finance.mapper;

import com.finance.domain.LoanMemo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface LoanMemoMapper {
    void insertMemo(LoanMemo memo);
    List<LoanMemo> selectMemos(@Param("applicationId") String applicationId);
    void deleteMemo(@Param("memoId") int memoId);
}
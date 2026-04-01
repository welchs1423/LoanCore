package com.finance.mapper;

import com.finance.domain.LoanMemo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface LoanMemoMapper {
    
    @Insert("INSERT INTO LOAN_MEMO (application_id, memo_text) VALUES (#{applicationId}, #{memoText})")
    void insertMemo(LoanMemo memo);

    @Select("SELECT memo_id as memoId, application_id as applicationId, memo_text as memoText, created_at as createdAt FROM LOAN_MEMO WHERE application_id = #{applicationId} ORDER BY created_at DESC")
    List<LoanMemo> selectMemosByAppId(String applicationId);
}
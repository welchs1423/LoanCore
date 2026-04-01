package com.finance.mapper;

import com.finance.domain.LoanMemo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface LoanMemoMapper {
    
    @Select("SELECT memo_id as memoId, application_id as applicationId, writer, content, created_at as createdAt FROM LOAN_MEMO WHERE application_id = #{applicationId} ORDER BY created_at DESC")
    List<LoanMemo> selectMemos(String applicationId);

    @Insert("INSERT INTO LOAN_MEMO (application_id, writer, content) VALUES (#{applicationId}, #{writer}, #{content})")
    void insertMemo(LoanMemo memo);

    @Delete("DELETE FROM LOAN_MEMO WHERE memo_id = #{memoId}")
    void deleteMemo(int memoId);
}
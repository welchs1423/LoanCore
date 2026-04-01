package com.finance.mapper;

import com.finance.domain.LoanApplication;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface LoanMapper {
    @Select("SELECT COUNT(*) FROM loan_applications WHERE del_yn = 'N'")
    int countAllApplications();

    @Select("SELECT * FROM loan_applications WHERE del_yn = 'N' ORDER BY applied_at DESC")
    List<LoanApplication> selectAllApplications();

    @Insert("INSERT INTO loan_applications (application_id, customer_id, amount, status_code) VALUES (#{applicationId}, #{customerId}, #{amount}, 'PENDING')")
    void insertApplication(LoanApplication app);

    @Select("SELECT * FROM loan_applications WHERE application_id = #{id}")
    LoanApplication selectApplicationById(String id);

    @Update("UPDATE loan_applications SET del_yn = 'Y' WHERE application_id = #{id}")
    void deleteApplication(String id);

    @Update("UPDATE loan_applications SET customer_id = #{customerId}, amount = #{amount} WHERE application_id = #{applicationId}")
    void updateApplication(LoanApplication app);

    @Select("<script>SELECT * FROM loan_applications WHERE del_yn = 'N' <if test='keyword != null'>AND customer_id LIKE CONCAT('%',#{keyword},'%')</if> ORDER BY applied_at DESC</script>")
    List<LoanApplication> searchApplicationsDynamic(@Param("keyword") String keyword, @Param("status") String status, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("SELECT * FROM loan_applications WHERE del_yn = 'N' ORDER BY applied_at DESC LIMIT #{pageSize} OFFSET #{offset}")
    List<LoanApplication> selectApplicationsWithPaging(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Update("<script>UPDATE loan_applications SET status_code = #{status} WHERE application_id IN <foreach item='id' collection='appIds' open='(' separator=',' close=')'>#{id}</foreach></script>")
    void updateApplicationStatusBulk(@Param("status") String status, @Param("appIds") List<String> appIds);
}
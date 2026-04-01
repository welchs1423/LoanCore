package com.finance.domain;

import java.util.Date;

public class LoanMemo {
    private Long memoId;
    private String applicationId;
    private String memoText;
    private Date createdAt;

    public Long getMemoId() { return memoId; }
    public void setMemoId(Long memoId) { this.memoId = memoId; }
    
    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
    
    public String getMemoText() { return memoText; }
    public void setMemoText(String memoText) { this.memoText = memoText; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
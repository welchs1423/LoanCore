package com.finance.domain;

import java.sql.Timestamp;

public class LoanMemo {
    private int memoId;
    private String applicationId;
    private String writer;
    private String content;
    private Timestamp createdAt;

    public int getMemoId() { return memoId; }
    public void setMemoId(int memoId) { this.memoId = memoId; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
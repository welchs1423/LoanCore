package com.finance.domain;

import java.sql.Timestamp;

public class AuditLog {
    private int logId;
    private String actionName;
    private String targetId;
    private String actionDetail;
    private Timestamp createdAt;

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public String getActionName() { return actionName; }
    public void setActionName(String actionName) { this.actionName = actionName; }
    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }
    public String getActionDetail() { return actionDetail; }
    public void setActionDetail(String actionDetail) { this.actionDetail = actionDetail; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
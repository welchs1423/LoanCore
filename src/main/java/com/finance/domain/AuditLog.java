package com.finance.domain;

public class AuditLog {
    private String actionName;
    private String targetId;
    private String actionDetail;

    // 기본 생성자 (Error: The constructor AuditLog() is undefined 해결)
    public AuditLog() {}

    // Getter / Setter (Error: The method set... is undefined 해결)
    public String getActionName() { return actionName; }
    public void setActionName(String actionName) { this.actionName = actionName; }

    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }

    public String getActionDetail() { return actionDetail; }
    public void setActionDetail(String actionDetail) { this.actionDetail = actionDetail; }
}
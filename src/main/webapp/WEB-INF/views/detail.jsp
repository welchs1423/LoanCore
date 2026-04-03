<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>대출 상세 조회 - LoanCore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>대출 신청 상세 정보</h2>
        <a href="/LoanCore/" class="btn btn-outline-secondary">메인으로</a>
    </div>
    
    <div class="card p-4 shadow-sm mb-4">
        <p><strong>신청 번호:</strong> ${app.applicationId}</p>
        <p><strong>고객 ID:</strong> ${app.customerId}</p>
        <p><strong>신청 금액:</strong> ${app.amount} 원</p>
        <p><strong>상태:</strong> <span class="badge bg-primary">${app.statusCode}</span></p>
        
        <hr>
        <h5>📁 증빙 서류</h5>
        <c:choose>
            <c:when test="${not empty app.fileName}">
                <div class="d-flex align-items-center gap-3 mt-2">
                    <span>${app.fileName}</span>
                    <a href="/LoanCore/download?fileName=${app.fileName}" class="btn btn-sm btn-success">다운로드</a>
                </div>
            </c:when>
            <c:otherwise>
                <p class="text-muted mt-2">첨부된 파일이 없습니다.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="memo-section card p-4 shadow-sm">
        <h4 class="mb-3"><spring:message code="memo.title" /></h4>
        
        <div class="input-group mb-3">
            <input type="text" id="memoInput" class="form-control" placeholder="<spring:message code='memo.placeholder' />">
            <button type="button" class="btn btn-primary" onclick="addMemo()">
                <spring:message code="memo.btn.add" />
            </button>
        </div>

        <div id="memoList" class="p-3 bg-light rounded">
            <p class="empty-msg text-muted mb-0"><spring:message code="memo.empty" /></p>
        </div>
    </div>
</body>
</html>
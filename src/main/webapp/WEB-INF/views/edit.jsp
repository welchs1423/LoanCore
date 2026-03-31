<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 정보 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">대출 신청 정보 수정</h2>
        
        <c:choose>
            <c:when test="${empty app}">
                <div class="alert alert-warning">잘못된 접근입니다.</div>
                <a href="list" class="btn btn-primary">목록으로 돌아가기</a>
            </c:when>
            <c:otherwise>
                <form action="edit" method="post" class="card p-4 shadow-sm">
                    <input type="hidden" name="id" value="${app.applicationId}">
                    
                    <div class="mb-3">
                        <label class="form-label">신청 번호</label>
                        <input type="text" class="form-control" value="${app.applicationId}" disabled>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">고객 ID</label>
                        <input type="text" class="form-control" name="customerId" value="${app.customerId}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">신청 금액 (원)</label>
                        <input type="number" class="form-control" name="amount" value="${app.amount}" required>
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-success">수정 완료</button>
                        <a href="detail?id=${app.applicationId}" class="btn btn-secondary">취소</a>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
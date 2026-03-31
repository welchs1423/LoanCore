<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 신청 내역 조회</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">대출 신청 내역 목록</h2>
        <table class="table table-hover table-bordered shadow-sm">
            <thead class="table-light">
                <tr>
                    <th>신청 번호</th>
                    <th>고객 ID</th>
                    <th>신청 금액 (원)</th>
                    <th>진행 상태</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty loanList}">
                        <tr>
                            <td colspan="4" class="text-center">신청 내역이 없습니다.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="app" items="${loanList}">
                            <tr>
                                <td><a href="detail?id=${app.applicationId}">${app.applicationId}</a></td>
                                <td>${app.customerId}</td>
                                <td>${app.amount}</td>
                                <td>
                                    <c:if test="${app.statusCode == 'APPROVE'}">
                                        <span class="badge bg-success">승인</span>
                                    </c:if>
                                    <c:if test="${app.statusCode == 'REJECT'}">
                                        <span class="badge bg-danger">거절</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        <div class="mt-3">
            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
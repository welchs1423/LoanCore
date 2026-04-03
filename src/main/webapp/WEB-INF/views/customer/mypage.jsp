<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 마이페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">나의 대출 현황</h2>
    
    <div class="card mb-4">
        <div class="card-body">
            <table class="table table-bordered mb-0">
                <tr>
                    <th class="bg-light w-25">신청 번호</th>
                    <td>${loanInfo.applicationId}</td>
                    <th class="bg-light w-25">현재 상태</th>
                    <td><span class="badge bg-primary">${loanInfo.statusCode}</span></td>
                </tr>
                <tr>
                    <th class="bg-light">대출 잔액</th>
                    <td colspan="3" class="text-danger fw-bold">
                        <fmt:formatNumber value="${loanInfo.amount}" pattern="#,###"/> 원
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <h3 class="mb-3">예상 상환 스케줄</h3>
    <table class="table table-striped table-hover text-center">
        <thead class="table-dark">
            <tr>
                <th>회차(월)</th>
                <th>월 상환액</th>
                <th>납입 원금</th>
                <th>납입 이자</th>
                <th>상환 후 잔액</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${repaymentSchedule}" var="row">
                <tr>
                    <td>${row.month} 회차</td>
                    <td><fmt:formatNumber value="${row.payment}" pattern="#,###"/> 원</td>
                    <td><fmt:formatNumber value="${row.principal}" pattern="#,###"/> 원</td>
                    <td><fmt:formatNumber value="${row.interest}" pattern="#,###"/> 원</td>
                    <td><fmt:formatNumber value="${row.balance}" pattern="#,###"/> 원</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
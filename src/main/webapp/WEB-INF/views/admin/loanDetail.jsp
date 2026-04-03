<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 상세 관리</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">대출 원장 상세 정보</h2>
    
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-dark text-white">
            기본 정보 (신청번호: ${loan.applicationId})
        </div>
        <div class="card-body">
            <table class="table table-bordered">
                <tr>
                    <th class="bg-light w-25">고객 ID</th>
                    <td>${loan.customerId}</td>
                    <th class="bg-light w-25">현재 상태</th>
                    <td><span class="badge bg-info text-dark">${loan.statusCode}</span></td>
                </tr>
                <tr>
                    <th class="bg-light">대출 원금/잔액</th>
                    <td colspan="3" class="text-danger fw-bold">
                        <fmt:formatNumber value="${loan.amount}" pattern="#,###"/> 원
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="d-flex justify-content-end gap-2">
        <button class="btn btn-warning">추심 활동 등록</button>
        <button class="btn btn-danger">NPL 매각 처리</button>
        <button class="btn btn-secondary">목록으로</button>
    </div>
</div>
</body>
</html>
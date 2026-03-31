<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 심사 결과</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">대출 심사 결과</h2>
        <div class="card p-4 shadow-sm">
            <p><strong>고객 ID:</strong> ${customerId}</p>
            <p><strong>신청 금액:</strong> ${amount}원</p>
            <hr>
            <h4 class="text-primary">심사 상태: ${statusCode}</h4>
            <p class="alert alert-info mt-3">${reviewMessage}</p>
        </div>
        <div class="mt-3">
            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
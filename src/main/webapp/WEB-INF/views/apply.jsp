<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 신청</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">LoanCore 대출 신청</h2>
        <form action="submit-loan" method="post" class="card p-4 shadow-sm">
            <div class="mb-3">
                <label class="form-label">고객 ID</label>
                <input type="text" class="form-control" name="customerId" placeholder="CUST-001" required>
            </div>
            <div class="mb-3">
                <label class="form-label">신청 금액 (원)</label>
                <input type="number" class="form-control" name="amount" placeholder="50000000" required>
            </div>
            <button type="submit" class="btn btn-primary">대출 심사 요청</button>
        </form>
        <div class="mt-3">
            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
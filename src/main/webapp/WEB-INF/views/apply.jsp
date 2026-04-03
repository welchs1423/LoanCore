<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>대출 신청</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <h2>대출 신청</h2>
    <form action="submit-loan" method="post" class="mt-4" enctype="multipart/form-data">
        <div class="mb-3">
            <label class="form-label">고객 ID</label>
            <input type="text" name="customerId" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">신청 금액 (원)</label>
            <input type="number" name="amount" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">거주지 주소</label>
            <input type="text" name="address" class="form-control" required>
        </div>
        
        <div class="mb-3">
            <label class="form-label">증빙 서류 첨부</label>
            <input class="form-control" type="file" name="uploadFile">
        </div>

        <button type="submit" class="btn btn-primary">신청하기</button>
        <a href="./" class="btn btn-secondary">취소</a>
    </form>
</body>
</html>
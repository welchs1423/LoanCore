<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>시스템 오류 - LoanCore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">
    <div class="text-center p-5 bg-white rounded shadow-sm">
        <h1 class="display-1 text-danger fw-bold">500</h1>
        <h3 class="mb-4">시스템 처리 중 오류가 발생했습니다.</h3>
        <p class="text-muted mb-4">${errorMessage}</p>
        <a href="/LoanCore/" class="btn btn-primary">메인으로 돌아가기</a>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 시스템 오류</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center vh-100">
    <div class="container text-center">
        <h1 class="display-1 fw-bold text-danger">500</h1>
        <h2 class="mb-4">시스템 처리 중 오류가 발생했습니다.</h2>
        <p class="text-muted mb-4">불편을 드려 죄송합니다. 잠시 후 다시 시도해 주시거나 관리자에게 문의해 주십시오.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">메인으로 돌아가기</a>
    </div>
</body>
</html>
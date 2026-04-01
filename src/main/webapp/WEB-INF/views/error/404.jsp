<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 페이지를 찾을 수 없습니다</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center vh-100">
    <div class="container text-center">
        <h1 class="display-1 fw-bold text-secondary">404</h1>
        <h2 class="mb-4">요청하신 페이지를 찾을 수 없습니다.</h2>
        <p class="text-muted mb-4">입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">메인으로 돌아가기</a>
    </div>
</body>
</html>
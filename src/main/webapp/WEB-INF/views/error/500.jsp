<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>시스템 오류 발생 - LoanCore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .error-container { text-align: center; margin-top: 100px; }
        .error-code { font-size: 8rem; font-weight: bold; color: #dc3545; }
        .error-message { font-size: 1.5rem; color: #6c757d; margin-bottom: 30px; }
    </style>
</head>
<body>
    <div class="container error-container">
        <div class="error-code">500</div>
        <div class="error-message">시스템 오류가 발생했습니다.</div>
        <p class="mb-4">서비스 이용에 불편을 드려 죄송합니다. 잠시 후 다시 시도해 주세요.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg"><i class="fa-solid fa-house"></i> 메인으로 돌아가기</a>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 시스템 오류</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5 text-center">
        <h1 class="text-danger mb-4">시스템 오류가 발생했습니다.</h1>
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
        <a href="./" class="btn btn-primary mt-3">메인 화면으로 돌아가기</a>
    </div>
</body>
</html>
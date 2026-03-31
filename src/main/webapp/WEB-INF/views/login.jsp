<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 관리자 로그인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center vh-100">
    <div class="container" style="max-width: 400px;">
        <div class="card shadow-sm p-4">
            <h3 class="text-center mb-4">관리자 로그인</h3>
            
            <c:if test="${not empty loginError}">
                <div class="alert alert-danger py-2">${loginError}</div>
            </c:if>

            <form action="login" method="post">
                <div class="mb-3">
                    <label class="form-label">아이디</label>
                    <input type="text" class="form-control" name="adminId" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">비밀번호</label>
                    <input type="password" class="form-control" name="adminPw" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">로그인</button>
            </form>
            <div class="mt-3 text-center">
                <a href="./" class="text-decoration-none">메인으로 돌아가기</a>
            </div>
        </div>
    </div>
</body>
</html>
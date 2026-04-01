<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 로그인 - LoanCore</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-box { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 300px; text-align: center; }
        .login-box h2 { margin-bottom: 20px; color: #333; }
        .login-box input { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn-login { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; font-weight: bold; }
        .error-msg { color: red; font-size: 12px; margin-bottom: 15px; display: none; }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>LoanCore Admin</h2>
        <% if ("true".equals(request.getParameter("error"))) { %>
            <div class="error-msg" style="display:block;">아이디 또는 비밀번호가 일치하지 않습니다.</div>
        <% } %>
        <form action="/LoanCore/doLogin" method="post">
            <input type="text" name="adminId" placeholder="관리자 ID (admin)" required>
            <input type="password" name="adminPw" placeholder="비밀번호 (admin123)" required>
            <button type="submit" class="btn-login">로그인</button>
        </form>
    </div>
</body>
</html>
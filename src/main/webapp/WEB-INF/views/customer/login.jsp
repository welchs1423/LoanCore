<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>고객 로그인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 400px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="text-center mb-4">대출 조회 로그인</h3>
            <form action="${pageContext.request.contextPath}/customer/login" method="post">
                <div class="mb-3">
                    <label class="form-label">대출 신청 번호</label>
                    <input type="text" name="applicationId" class="form-control" placeholder="예: L16800000000" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">나의 대출 조회하기</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
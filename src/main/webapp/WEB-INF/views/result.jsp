<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 심사 결과</title>
</head>
<body>
    <h2>대출 심사 결과</h2>
    <p>고객 ID: ${customerId}</p>
    <p>신청 금액: ${amount}원</p>
    <hr>
    <h3>심사 상태: ${statusCode}</h3>
    <p>상세 내용: ${reviewMessage}</p>
    <br>
    <a href="./">메인으로 돌아가기</a>
</body>
</html>
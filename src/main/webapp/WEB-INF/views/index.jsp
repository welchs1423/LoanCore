<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>LoanCore - 여신 시스템</title>
</head>
<body>
    <h2>=== LoanCore 여신 시스템 메인 ===</h2>
    <p>현재 날짜: <%= new java.util.Date() %></p>
    <button onclick="location.href='test-loan'">대출 심사 테스트 실행</button>
    <button onclick="location.href='apply'">대출 신청서 작성하기</button>
    <a href="list" class="btn btn-info mt-3">신청 내역 조회하기</a>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>LoanCore - 대출신청</title>
</head>
<body>
	<h2>=== 대출 신청서 작성===</h2>
	<form action="submit-loan" method="post">
		<label>고객 ID:</label>
		<input type="text" name="customerId" placeholder="예: CUST-001" required><br><br>
		
		<label>신청 금액 (원):</label>
		<input type="number" name="amount" placeholder="예: 50000000" required><br><br>
		
		<button type="submit">대출 심사 요청</button>
	</form>
	<br>
	
	<a href="./">메인으로 돌아가기</a>
</body>
</html>
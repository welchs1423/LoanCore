<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 신청</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">LoanCore 대출 신청</h2>

        <c:if test="${not empty errors}">
            <div class="alert alert-danger">
                <ul class="mb-0">
                    <c:forEach var="error" items="${errors}">
                        <li>${error.defaultMessage}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="submit-loan" method="post" class="card p-4 shadow-sm">
            <div class="mb-3">
                <label class="form-label">고객 ID</label>
                <div class="input-group">
                    <input type="text" class="form-control" name="customerId" id="customerId" placeholder="CUST-001" required>
                    <button type="button" class="btn btn-outline-info" onclick="checkCustomerStatus()">진행 상태 검사</button>
                </div>
                <div id="customerCheckResult" class="form-text mt-2"></div>
            </div>
            
            <div class="mb-3">
                <label class="form-label">신청 금액 (원)</label>
                <input type="number" class="form-control" name="amount" placeholder="50000000" required>
            </div>
            <button type="submit" class="btn btn-primary">대출 심사 요청</button>
        </form>
        
        <div class="mt-3">
            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
        </div>
    </div>

    <script>
        function checkCustomerStatus() {
            const customerId = document.getElementById('customerId').value;
            if (!customerId) {
                alert('고객 ID를 먼저 입력해주세요.');
                return;
            }

            fetch('api/check-customer?customerId=' + customerId)
                .then(response => response.json())
                .then(data => {
                    const resultDiv = document.getElementById('customerCheckResult');
                    if (data.exists) {
                        resultDiv.innerHTML = '<span class="text-danger fw-bold">' + data.message + '</span>';
                    } else {
                        resultDiv.innerHTML = '<span class="text-success fw-bold">' + data.message + '</span>';
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    </script>
</body>
</html>
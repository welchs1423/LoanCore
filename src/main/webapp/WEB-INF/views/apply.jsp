<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 신청</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2>대출 신청</h2>

        <c:if test="${not empty errors}">
            <div class="alert alert-danger">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error.defaultMessage}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form:form action="submit-loan" method="post" enctype="multipart/form-data" modelAttribute="app">
            <div class="mb-3">
                <label for="customerId" class="form-label">고객 ID</label>
                <form:input path="customerId" class="form-control" id="customerId" />
                <form:errors path="customerId" cssClass="text-danger" />
            </div>
            
            <div class="mb-3">
                <label for="amount" class="form-label">신청 금액 (원)</label>
                <form:input path="amount" type="number" class="form-control" id="amount" onkeyup="checkLimit()"/>
                <div id="limitMessage" class="form-text"></div>
                <form:errors path="amount" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <label for="address" class="form-label">거주지 주소</label>
                <form:input path="address" class="form-control" id="address" />
                <form:errors path="address" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <label for="uploadFile" class="form-label">증빙 서류 (선택)</label>
                <input type="file" class="form-control" id="uploadFile" name="uploadFile">
            </div>

            <button type="submit" class="btn btn-primary" id="submitBtn">신청하기</button>
            <a href="./" class="btn btn-secondary">취소</a>
        </form:form>
    </div>

    <script>
        function checkLimit() {
            const amountInput = document.getElementById('amount').value;
            const messageDiv = document.getElementById('limitMessage');
            const submitBtn = document.getElementById('submitBtn');

            // 입력값이 없으면 메시지 초기화
            if (!amountInput) {
                messageDiv.innerHTML = '';
                submitBtn.disabled = false;
                return;
            }

            // Fetch API를 사용하여 서버에 한도 조회 요청
            fetch(`api/check-limit?amount=${amountInput}`)
                .then(response => response.json())
                .then(data => {
                    if (data.isAvailable) {
                        messageDiv.innerHTML = `<span class="text-success">${data.message}</span>`;
                        submitBtn.disabled = false; // 신청 버튼 활성화
                    } else {
                        messageDiv.innerHTML = `<span class="text-danger">${data.message}</span>`;
                        submitBtn.disabled = true;  // 한도 초과 시 신청 버튼 비활성화
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    messageDiv.innerHTML = '<span class="text-danger">한도 조회 중 오류가 발생했습니다.</span>';
                });
        }
    </script>
</body>
</html>
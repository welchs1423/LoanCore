<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 신청</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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

        <form id="loanForm" action="submit-loan" method="post" enctype="multipart/form-data" class="card p-4 shadow-sm">
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

            <div class="mb-3">
                <label class="form-label">거주지 주소</label>
                <div class="input-group mb-2">
                    <input type="text" id="postcode" class="form-control" placeholder="우편번호" readonly>
                    <button type="button" class="btn btn-outline-secondary" onclick="execDaumPostcode()">우편번호 찾기</button>
                </div>
                <input type="text" id="roadAddress" class="form-control mb-2" placeholder="도로명주소" readonly>
                <input type="text" id="detailAddress" class="form-control" placeholder="상세주소를 입력하세요" required>
                <input type="hidden" name="address" id="fullAddress">
            </div>

            <div class="mb-3">
                <label class="form-label">증빙 서류 첨부</label>
                <input type="file" class="form-control" name="uploadFile">
            </div>
            <button type="submit" class="btn btn-primary">대출 심사 요청</button>
        </form>
        
        <div class="mt-3">
            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
        </div>
    </div>

    <script>
        // 진행 상태 검사
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

        // 다음 우편번호 API 호출
        function execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    document.getElementById('postcode').value = data.zonecode;
                    document.getElementById('roadAddress').value = data.roadAddress;
                    document.getElementById('detailAddress').focus();
                }
            }).open();
        }

        // 폼 전송 시 주소 합치기
        document.getElementById('loanForm').addEventListener('submit', function(e) {
            const post = document.getElementById('postcode').value;
            const road = document.getElementById('roadAddress').value;
            const detail = document.getElementById('detailAddress').value;
            
            if (post && road) {
                document.getElementById('fullAddress').value = '[' + post + '] ' + road + ' ' + detail;
            } else {
                document.getElementById('fullAddress').value = detail; // API 미사용 수기 입력 대비
            }
        });
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>대출 신청</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="card shadow-sm max-w-md mx-auto" style="max-width: 600px;">
            <div class="card-header bg-dark text-white">
                <h5 class="mb-0">신용대출 신청</h5>
            </div>
            <div class="card-body">
                <form id="loanApplyForm">
                    <div class="mb-3">
                        <label class="form-label">이름</label>
                        <input type="text" class="form-control" name="customerName" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">주민등록번호</label>
                        <input type="text" class="form-control" name="residentNumber" placeholder="xxxxxx-xxxxxxx" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">신청 금액 (원)</label>
                        <input type="number" class="form-control" name="amount" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">거주지 주소</label>
                        <div class="input-group mb-2">
                            <input type="text" class="form-control" id="postcode" placeholder="우편번호" readonly>
                            <button class="btn btn-outline-secondary" type="button" onclick="execDaumPostcode()">주소 검색</button>
                        </div>
                        <input type="text" class="form-control mb-2" id="address" placeholder="기본 주소" readonly>
                        <input type="text" class="form-control" id="detailAddress" placeholder="상세 주소">
                    </div>
                    <button type="submit" class="btn btn-primary w-100">신청완료</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        function execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    document.getElementById('postcode').value = data.zonecode;
                    document.getElementById('address').value = data.address;
                    document.getElementById('detailAddress').focus();
                }
            }).open();
        }

        document.getElementById('loanApplyForm').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('대출 신청이 접수되었습니다. (API 연동 생략)');
            location.href = '/admin/dashboard';
        });
    </script>
</body>
</html>
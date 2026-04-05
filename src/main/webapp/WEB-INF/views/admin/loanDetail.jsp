<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대출 상세 관리 - LoanCore Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="#">LoanCore Admin</a>
        </div>
    </nav>

    <div class="container">
        <h3 class="mb-4">대출 심사 및 관리</h3>

        <div class="card shadow-sm mb-4">
            <div class="card-header bg-white">
                <h5 class="card-title mb-0">여신 기본 정보</h5>
            </div>
            <div class="card-body">
                <div class="row mb-2">
                    <div class="col-md-3 fw-bold">신청 번호</div>
                    <div class="col-md-9" id="displayAppId">L001</div>
                </div>
                <div class="row mb-2">
                    <div class="col-md-3 fw-bold">고객명</div>
                    <div class="col-md-9">김철수 (고객번호: C9982)</div>
                </div>
                <div class="row mb-2">
                    <div class="col-md-3 fw-bold">대출 잔액</div>
                    <div class="col-md-9">50,000,000 원</div>
                </div>
                <div class="row mb-2">
                    <div class="col-md-3 fw-bold">현재 상태</div>
                    <div class="col-md-9"><span class="badge bg-success">ACTIVE</span></div>
                </div>
            </div>
        </div>

        <div class="card shadow-sm border-primary mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">만기 연장 심사 처리</h5>
            </div>
            <div class="card-body">
                <p class="text-muted">고객의 신용도 및 현재 연체 이력을 확인한 후 연장 처리를 진행하십시오.</p>
                <div class="row align-items-center">
                    <div class="col-md-4">
                        <div class="input-group">
                            <input type="number" id="extendMonths" class="form-control" value="12" min="1" max="60">
                            <span class="input-group-text">개월</span>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <button type="button" id="btnExtend" class="btn btn-primary px-4">
                            <span id="btnSpinner" class="spinner-border spinner-border-sm d-none" role="status" aria-hidden="true"></span>
                            연장 승인
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const btnExtend = document.getElementById('btnExtend');
            const extendMonths = document.getElementById('extendMonths');
            const btnSpinner = document.getElementById('btnSpinner');
            const applicationId = document.getElementById('displayAppId').innerText;

            btnExtend.addEventListener('click', function() {
                const monthsValue = parseInt(extendMonths.value, 10);

                if (isNaN(monthsValue) || monthsValue <= 0) {
                    alert('올바른 연장 개월 수를 입력하십시오.');
                    return;
                }

                if (!confirm(monthsValue + '개월 만기 연장을 승인하시겠습니까?')) {
                    return;
                }

                btnExtend.disabled = true;
                btnSpinner.classList.remove('d-none');

                const requestPayload = {
                    applicationId: applicationId,
                    months: monthsValue
                };

                fetch('/api/loan/extend', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + sessionStorage.getItem('jwt_token') 
                    },
                    body: JSON.stringify(requestPayload)
                })
                .then(response => {
                    return response.json().then(data => ({
                        status: response.status,
                        body: data
                    }));
                })
                .then(result => {
                    const data = result.body;
                    
                    if (result.status === 200 && data.status === 200) {
                        alert('만기 연장이 정상적으로 처리되었습니다.');
                        location.reload(); 
                    } else {
                        alert('처리 실패: ' + data.message + '\n에러 코드: ' + data.code);
                    }
                })
                .catch(error => {
                    console.error('API Error:', error);
                    alert('서버 통신 중 예기치 않은 오류가 발생했습니다.');
                })
                .finally(() => {
                    btnExtend.disabled = false;
                    btnSpinner.classList.add('d-none');
                });
            });
        });
    </script>
</body>
</html>
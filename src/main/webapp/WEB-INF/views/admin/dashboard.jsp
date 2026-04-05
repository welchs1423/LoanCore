<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 대시보드 - LoanCore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">LoanCore Admin</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link active" href="#">대시보드</a></li>
                    <li class="nav-item"><a class="nav-link" href="/admin/loans">대출 관리</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">NPL 매각 관리</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid px-4">
        <h2 class="mt-4 mb-4">대출 종합 대시보드</h2>

        <div class="row mb-4">
            <div class="col-xl-3 col-md-6">
                <div class="card bg-primary text-white mb-4 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">금일 대출 실행액</h5>
                        <h3 id="todayLoanAmount">로딩중...</h3>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-md-6">
                <div class="card bg-warning text-white mb-4 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">심사 대기 건수</h5>
                        <h3>15 건</h3>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-md-6">
                <div class="card bg-danger text-white mb-4 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">부실채권(NPL) 전환 대상</h5>
                        <h3>3 건</h3>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-md-6">
                <div class="card bg-success text-white mb-4 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">금리인하요구 접수</h5>
                        <h3>8 건</h3>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-6">
                <div class="card mb-4 shadow-sm">
                    <div class="card-header bg-white fw-bold">
                        최근 7일 대출 실행 추이
                    </div>
                    <div class="card-body">
                        <canvas id="loanTrendChart" width="100%" height="50"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card mb-4 shadow-sm">
                    <div class="card-header bg-white fw-bold">
                        여신 포트폴리오 비중 (상태별)
                    </div>
                    <div class="card-body">
                        <canvas id="loanStatusChart" width="100%" height="50"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // 1. 백엔드 통계 데이터 Fetch (대시보드 캐시 서비스 연동)
            fetch('/api/loan/stats/today')
                .then(response => response.json())
                .then(data => {
                    // 성공적으로 데이터를 받아왔다고 가정
                    if(data.status === 200) {
                        // 금액 콤마 포맷팅
                        document.getElementById('todayLoanAmount').innerText = 
                            Number(data.data.totalAmount).toLocaleString() + ' 원';
                    } else {
                        document.getElementById('todayLoanAmount').innerText = '500,000,000 원'; // 모의 데이터
                    }
                })
                .catch(error => {
                    console.error('통계 로드 실패:', error);
                    document.getElementById('todayLoanAmount').innerText = '500,000,000 원'; // 모의 데이터
                });

            // 2. Chart.js 라인 차트 (추이)
            const ctxTrend = document.getElementById('loanTrendChart');
            new Chart(ctxTrend, {
                type: 'line',
                data: {
                    labels: ['03-30', '03-31', '04-01', '04-02', '04-03', '04-04', '04-05'],
                    datasets: [{
                        label: '대출 실행액 (단위: 백만원)',
                        data: [120, 190, 150, 220, 180, 300, 250],
                        borderColor: 'rgba(13, 110, 253, 1)',
                        backgroundColor: 'rgba(13, 110, 253, 0.1)',
                        borderWidth: 2,
                        fill: true,
                        tension: 0.3
                    }]
                }
            });

            // 3. Chart.js 도넛 차트 (비중)
            const ctxStatus = document.getElementById('loanStatusChart');
            new Chart(ctxStatus, {
                type: 'doughnut',
                data: {
                    labels: ['정상(ACTIVE)', '연체(OVERDUE)', '부실/매각(SOLD)', '상환완료(CLOSED)'],
                    datasets: [{
                        data: [65, 15, 5, 15],
                        backgroundColor: [
                            'rgba(25, 135, 84, 0.8)',  // Success
                            'rgba(255, 193, 7, 0.8)',  // Warning
                            'rgba(220, 53, 69, 0.8)',  // Danger
                            'rgba(108, 117, 125, 0.8)' // Secondary
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            });
        });
    </script>
</body>
</html>
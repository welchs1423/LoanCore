<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LoanCore - 관리자 대시보드</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container">
			<a class="navbar-brand" href="/LoanCore/">LoanCore Admin</a>
			<div class="collapse navbar-collapse">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link"
						href="/LoanCore/logout">로그아웃</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="row mb-4">
			<div class="col-md-4">
				<div class="card text-white bg-primary h-100">
					<div class="card-body d-flex flex-column justify-content-center">
						<h5 class="card-title">전체 신청 건수</h5>
						<p class="card-text fs-1 fw-bold">${totalCount}건</p>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="card shadow-sm h-100">
					<div class="card-body">
						<h5 class="card-title">대출 상태 통계</h5>
						<div style="height: 200px;">
							<canvas id="loanChart"></canvas>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="card shadow-sm">
			<div class="card-body">
				<h4 class="card-title mb-4">최근 대출 신청 내역</h4>
				<table class="table table-hover align-middle">
					<thead class="table-light">
						<tr>
							<th>신청 번호</th>
							<th>고객 ID</th>
							<th>신청 금액</th>
							<th>상태</th>
							<th>신청 일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="app" items="${recentApps}">
							<tr>
								<td><a href="/LoanCore/detail/${app.applicationId}"
									class="text-decoration-none">${app.applicationId}</a></td>
								<td>${app.customerId}</td>
								<td><fmt:formatNumber value="${app.amount}" pattern="#,###" />
									원</td>
								<td><span
									class="badge 
                                        ${app.statusCode == 'PENDING' ? 'bg-warning' : 
                                          app.statusCode == 'APPROVED' ? 'bg-success' : 'bg-secondary'}">
										${app.statusCode} </span></td>
								<td><fmt:formatDate value="${app.appliedAt}"
										pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
						</c:forEach>
						<c:if test="${empty recentApps}">
							<tr>
								<td colspan="5" class="text-center text-muted py-4">최근 신청
									내역이 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="toast-container position-fixed bottom-0 end-0 p-3">
		<div id="liveToast" class="toast" role="alert" aria-live="assertive"
			aria-atomic="true">
			<div class="toast-header bg-primary text-white">
				<strong class="me-auto">실시간 알림</strong> <small>방금 전</small>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="toast" aria-label="Close"></button>
			</div>
			<div class="toast-body fw-bold" id="toastMessage"></div>
		</div>
	</div>

	<script>
        // 1. Chart.js 통계 데이터 렌더링
        document.addEventListener("DOMContentLoaded", function() {
            fetch('/LoanCore/api/statistics')
                .then(response => response.json())
                .then(data => {
                    const labels = data.map(item => item.statusCode || item.STATUSCODE);
                    const counts = data.map(item => item.count || item.COUNT);

                    const ctx = document.getElementById('loanChart').getContext('2d');
                    new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: '건수',
                                data: counts,
                                backgroundColor: ['#f6c23e', '#1cc88a', '#e74a3b', '#858796'],
                                borderWidth: 0
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    ticks: { stepSize: 1 }
                                }
                            }
                        }
                    });
                });
        });

     // 2. WebSocket 실시간 알림 연결
        let socket = new WebSocket("ws://" + location.host + "/LoanCore/ws/notifications");

        socket.onmessage = function(event) {
            // 메시지를 받으면 Toast 메시지 영역에 내용을 넣고 띄움
            document.getElementById('toastMessage').innerText = event.data;
            const toastElement = document.getElementById('liveToast');
            
            // 팝업이 다른 요소에 가려지지 않도록 z-index 강제 부여
            toastElement.style.zIndex = "9999"; 
            
            // delay: 5000 옵션으로 5초 동안 팝업 유지
            const toast = new bootstrap.Toast(toastElement, { delay: 5000 });
            toast.show();
            
            // 🚨 원흉이었던 location.reload() 삭제! 이제 팝업이 사라지지 않습니다.
        };

        socket.onclose = function() {
            console.log("WebSocket 연결이 종료되었습니다.");
        };
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 관리자 대시보드</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">대출 시스템 통합 대시보드</h2>
    
    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card text-white bg-primary shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">총 누적 대출 신청</h5>
                    <h2 class="mb-0">${totalCount} 건</h2>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-body">
                    <canvas id="statusChart"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    const ctx = document.getElementById('statusChart').getContext('2d');
    const statusChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['APPROVED', 'PENDING', 'OVERDUE', 'REJECTED', 'WRITTEN_OFF'],
            datasets: [{
                label: '상태별 대출 건수',
                data: [25, 10, 5, 8, 2], 
                backgroundColor: [
                    'rgba(54, 162, 235, 0.5)',
                    'rgba(255, 206, 86, 0.5)',
                    'rgba(255, 99, 132, 0.5)',
                    'rgba(153, 102, 255, 0.5)',
                    'rgba(0, 0, 0, 0.5)'
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 99, 132, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(0, 0, 0, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
</body>
</html>
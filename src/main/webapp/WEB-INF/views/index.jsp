<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 여신 시스템</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="d-flex justify-content-end mb-2">
            <c:choose>
                <c:when test="${not empty sessionScope.adminLogined}">
                    <span class="me-3 align-self-center">관리자님 환영합니다.</span>
                    <a href="logout" class="btn btn-sm btn-outline-danger">로그아웃</a>
                </c:when>
                <c:otherwise>
                    <a href="login" class="btn btn-sm btn-outline-primary">관리자 로그인</a>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="p-5 mb-4 bg-white rounded-3 shadow-sm text-center">
            <h1 class="display-5 fw-bold">LoanCore 여신 심사 시스템</h1>
            <p class="col-md-8 mx-auto fs-4">빠르고 정확한 대출 심사 프로세스</p>
            <div class="d-flex justify-content-center gap-3 mt-4">
                <a href="apply" class="btn btn-primary btn-lg">대출 신청하기</a>
                <c:if test="${not empty sessionScope.adminLogined}">
                    <a href="list" class="btn btn-outline-secondary btn-lg">신청 내역 조회</a>
                </c:if>
            </div>
        </div>

        <div class="row mb-5">
            <div class="col-md-7">
                <div class="row text-center h-100 align-content-center">
                    <div class="col-md-6 mb-4">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-body py-4">
                                <h5 class="card-title text-muted">총 신청 건수</h5>
                                <h2 class="display-6 fw-bold text-dark"><c:out value="${totalCount != null ? totalCount : 0}"/>건</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-4">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-body py-4">
                                <h5 class="card-title text-muted">심사 대기</h5>
                                <h2 class="display-6 fw-bold text-warning"><c:out value="${(totalCount != null ? totalCount : 0) - (approveCount != null ? approveCount : 0) - (rejectCount != null ? rejectCount : 0)}"/>건</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-body py-4">
                                <h5 class="card-title text-muted">심사 승인</h5>
                                <h2 class="display-6 fw-bold text-success"><c:out value="${approveCount != null ? approveCount : 0}"/>건</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card shadow-sm border-0 h-100">
                            <div class="card-body py-4">
                                <h5 class="card-title text-muted">심사 거절</h5>
                                <h2 class="display-6 fw-bold text-danger"><c:out value="${rejectCount != null ? rejectCount : 0}"/>건</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="col-md-5">
                <div class="card shadow-sm border-0 h-100 d-flex justify-content-center align-items-center p-3">
                    <h5 class="text-muted mb-3">대출 심사 현황 비율</h5>
                    <div style="position: relative; height:250px; width:250px;">
                        <canvas id="loanStatusChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let total = parseInt("${totalCount != null ? totalCount : 0}");
            let approve = parseInt("${approveCount != null ? approveCount : 0}");
            let reject = parseInt("${rejectCount != null ? rejectCount : 0}");
            let pending = total - approve - reject;

            if (total === 0) {
                pending = 1; 
            }

            const ctx = document.getElementById('loanStatusChart').getContext('2d');
            new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: ['승인', '거절', '대기'],
                    datasets: [{
                        data: total === 0 ? [0, 0, 1] : [approve, reject, pending],
                        backgroundColor: [
                            '#198754', 
                            '#dc3545', 
                            total === 0 ? '#e9ecef' : '#ffc107' 
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    cutout: '70%',
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                generateLabels: function(chart) {
                                    if (total === 0) return [];
                                    return Chart.defaults.plugins.legend.labels.generateLabels(chart);
                                }
                            }
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    if (total === 0) return '데이터 없음';
                                    let label = context.label || '';
                                    if (label) {
                                        label += ': ';
                                    }
                                    if (context.parsed !== null) {
                                        let percentage = Math.round((context.parsed / total) * 100) + '%';
                                        label += context.parsed + '건 (' + percentage + ')';
                                    }
                                    return label;
                                }
                            }
                        }
                    }
                }
            });
        });
    </script>
</body>
</html>
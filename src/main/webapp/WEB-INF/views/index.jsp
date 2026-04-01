<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<div class="container mt-5">
    <h2>대출 심사 대시보드</h2>
    <div class="row mt-4">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header">심사 현황 통계</div>
                <div class="card-body">
                    <canvas id="loanChart" width="400" height="200"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    const ctx = document.getElementById('loanChart').getContext('2d');
    const loanChart = new Chart(ctx, {
        type: 'pie', // 파이 차트
        data: {
            labels: ['승인(APPROVE)', '거절(REJECT)', '대기(PENDING)'],
            datasets: [{
                data: [${approveCount}, ${rejectCount}, ${totalCount - approveCount - rejectCount}],
                backgroundColor: ['#28a745', '#dc3545', '#ffc107']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'bottom' }
            }
        }
    });
</script>
<button type="button" id="btnExtend" class="btn btn-primary">대출 12개월 연장하기</button>

<script>
document.getElementById('btnExtend').addEventListener('click', function() {
    const requestData = {
        applicationId: 'L001',
        months: 12
    };

    // 백엔드 API 찌르기
    fetch('/api/loan/extend', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => response.json())
    .then(data => {
        // ApiResponse 규격에 맞춘 처리
        if (data.status === 200) {
            alert('대출이 성공적으로 연장되었습니다.');
            location.reload(); 
        } else {
            alert('오류 발생: ' + data.message + ' (코드: ' + data.code + ')');
        }
    })
    .catch(error => {
        alert('서버 통신 중 오류가 발생했습니다.');
    });
});
</script>
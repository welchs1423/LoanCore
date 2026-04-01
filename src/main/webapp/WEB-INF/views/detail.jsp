<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>대출 상세 정보</title>
    <style>
        body { font-family: sans-serif; margin: 40px; }
        .info-box, .memo-box { border: 1px solid #ddd; padding: 20px; border-radius: 5px; max-width: 500px; margin-bottom: 20px; }
        .info-box p { font-size: 16px; margin-bottom: 10px; }
        .btn { padding: 10px 15px; border: none; color: white; border-radius: 5px; cursor: pointer; font-size: 14px; }
        .btn-approve { background-color: #28a745; font-size: 16px; }
        .btn-memo { background-color: #17a2b8; margin-top: 10px; }
        .btn-back { background-color: #6c757d; text-decoration: none; display: inline-block; }
        textarea { width: 100%; height: 80px; padding: 10px; box-sizing: border-box; resize: none; }
        .memo-list { margin-top: 15px; border-top: 1px solid #eee; padding-top: 15px; }
        .memo-item { background: #f9f9f9; padding: 10px; border-left: 4px solid #17a2b8; margin-bottom: 10px; font-size: 14px; }
        .memo-date { font-size: 12px; color: #888; margin-bottom: 5px; display: block; }
    </style>
</head>
<body>
    <h1>대출 상세 정보</h1>

    <div class="info-box">
        <p><strong>신청 ID:</strong> <span id="appId">${app.applicationId}</span></p>
        <p><strong>고객 ID:</strong> ${app.customerId}</p>
        <p><strong>신청 금액:</strong> ${app.amount} 원</p>
        <p><strong>주소:</strong> ${app.address}</p>
        <p><strong>신청 일시:</strong> ${app.appliedAt}</p>
        <p><strong>현재 상태:</strong> 
            <span style="color: ${app.statusCode == 'APPROVED' ? 'green' : 'red'}; font-weight: bold;">
                ${app.statusCode}
            </span>
        </p>

        <form action="/LoanCore/approve" method="post" style="margin-top: 20px;">
            <input type="hidden" name="applicationId" value="${app.applicationId}">
            <button type="submit" class="btn btn-approve" ${app.statusCode == 'APPROVED' ? 'disabled' : ''}>
                ${app.statusCode == 'APPROVED' ? '승인 완료됨' : '대출 승인 처리'}
            </button>
        </form>
    </div>

    <div class="memo-box">
        <h3>심사 메모</h3>
        <textarea id="memoInput" placeholder="심사 관련 코멘트를 남겨주세요..."></textarea>
        <button onclick="submitMemo()" class="btn btn-memo">메모 등록</button>
        
        <div class="memo-list" id="memoList">
            </div>
    </div>

    <a href="/LoanCore/" class="btn btn-back">목록으로 돌아가기</a>

    <script>
        const appId = document.getElementById('appId').innerText;

        window.onload = function() {
            loadMemos();
        };

        function loadMemos() {
            fetch('/LoanCore/api/memos/' + appId)
                .then(response => response.json())
                .then(data => {
                    const memoList = document.getElementById('memoList');
                    memoList.innerHTML = '';
                    if(data.length === 0) {
                        memoList.innerHTML = '<p style="color:#888; font-size:14px;">등록된 메모가 없습니다.</p>';
                        return;
                    }
                    data.forEach(memo => {
                        const div = document.createElement('div');
                        div.className = 'memo-item';
                        
                        const dateSpan = document.createElement('span');
                        dateSpan.className = 'memo-date';
                        const dateObj = new Date(memo.createdAt);
                        dateSpan.innerText = dateObj.toLocaleString();
                        
                        const textNode = document.createTextNode(memo.memoText);
                        
                        div.appendChild(dateSpan);
                        div.appendChild(textNode);
                        memoList.appendChild(div);
                    });
                });
        }

        function submitMemo() {
            const memoText = document.getElementById('memoInput').value;
            if(!memoText.trim()) {
                alert('메모 내용을 입력하세요.');
                return;
            }

            const payload = {
                applicationId: appId,
                memoText: memoText
            };

            fetch('/LoanCore/api/memos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            })
            .then(response => response.text())
            .then(result => {
                if(result === 'success') {
                    document.getElementById('memoInput').value = '';
                    loadMemos();
                }
            })
            .catch(error => console.error('Error:', error));
        }
    </script>
</body>
</html>
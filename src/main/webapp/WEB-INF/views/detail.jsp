<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 상세 정보</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">대출 상세 정보</h2>
        
        <c:choose>
            <c:when test="${empty app}">
                <div class="alert alert-warning">해당 신청 내역을 찾을 수 없습니다.</div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-md-7">
                        <div class="card p-4 shadow-sm mb-4 position-relative">
                            <div class="position-absolute top-0 end-0 p-3">
                                <img src="api/qr?appId=${app.applicationId}" alt="QR Code" class="img-thumbnail" style="width: 90px; height: 90px;" title="모바일에서 확인하세요">
                            </div>
                            <h5 class="border-bottom pb-2 mb-3 w-75">신청 내역</h5>
                            <table class="table table-borderless">
                                <tr><th style="width: 150px;">신청 번호</th><td id="appId">${app.applicationId}</td></tr>
                                <tr><th>고객 ID</th><td>${app.customerId}</td></tr>
                                <tr><th>거주지 주소</th><td>${not empty app.address ? app.address : '<span class="text-muted">입력된 주소 없음</span>'}</td></tr>
                                <tr><th>신청 금액</th><td>${app.amount} 원</td></tr>
                                <tr>
                                    <th>진행 상태</th>
                                    <td>
                                        <c:if test="${app.statusCode == 'APPROVE'}"><span class="badge bg-success">승인</span></c:if>
                                        <c:if test="${app.statusCode == 'REJECT'}"><span class="badge bg-danger">거절</span></c:if>
                                        <c:if test="${app.statusCode == 'PENDING'}"><span class="badge bg-warning">대기</span></c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <th>증빙 서류</th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty app.fileName}">
                                                <a href="download?fileName=${app.fileName}" class="btn btn-sm btn-outline-primary">파일 다운로드</a>
                                                <span class="text-muted ms-2">${app.fileName}</span>
                                            </c:when>
                                            <c:otherwise><span class="text-muted">첨부파일 없음</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        
                        <div class="d-flex gap-2">
                            <a href="list" class="btn btn-primary">목록으로</a>
                            <a href="edit?id=${app.applicationId}" class="btn btn-warning">정보 수정</a>
                            <form action="delete" method="post" onsubmit="return confirm('정말 이 신청 건을 취소하시겠습니까?');">
                                <input type="hidden" name="id" value="${app.applicationId}">
                                <button type="submit" class="btn btn-danger">신청 취소</button>
                            </form>
                            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
                        </div>
                    </div>

                    <div class="col-md-5">
                        <div class="card shadow-sm h-100">
                            <div class="card-header bg-light fw-bold">📝 관리자 심사 메모</div>
                            <div class="card-body d-flex flex-column">
                                <div id="memoList" class="flex-grow-1 overflow-auto mb-3" style="max-height: 400px;">
                                </div>
                                <div class="input-group mt-auto">
                                    <input type="text" id="memoInput" class="form-control" placeholder="메모를 입력하세요..." onkeypress="if(event.keyCode==13) addMemo();">
                                    <button class="btn btn-dark" type="button" onclick="addMemo()">등록</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        const appId = document.getElementById('appId') ? document.getElementById('appId').innerText : null;

        document.addEventListener("DOMContentLoaded", function() {
            if(appId) loadMemos();
        });

        function loadMemos() {
            fetch('api/memos?applicationId=' + appId)
                .then(response => response.json())
                .then(data => {
                    const listDiv = document.getElementById('memoList');
                    listDiv.innerHTML = '';
                    if(data.length === 0) {
                        listDiv.innerHTML = '<p class="text-muted text-center mt-3">등록된 메모가 없습니다.</p>';
                        return;
                    }
                    data.forEach(memo => {
                        const date = new Date(memo.createdAt).toLocaleString();
                        listDiv.innerHTML += 
                            '<div class="border-bottom pb-2 mb-2">' +
                                '<div class="d-flex justify-content-between align-items-center mb-1">' +
                                    '<strong>' + memo.writer + '</strong>' +
                                    '<div>' +
                                        '<small class="text-muted me-2">' + date + '</small>' +
                                        '<button class="btn btn-sm btn-outline-danger py-0 px-1" onclick="deleteMemo(' + memo.memoId + ')">X</button>' +
                                    '</div>' +
                                '</div>' +
                                '<span>' + memo.content + '</span>' +
                            '</div>';
                    });
                });
        }

        function addMemo() {
            const content = document.getElementById('memoInput').value;
            if(!content.trim()) return alert("내용을 입력하세요.");

            const formData = new URLSearchParams();
            formData.append('applicationId', appId);
            formData.append('content', content);

            fetch('api/memos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if(data.success) {
                    document.getElementById('memoInput').value = '';
                    loadMemos();
                }
            });
        }

        function deleteMemo(memoId) {
            if(!confirm("이 메모를 삭제하시겠습니까?")) return;

            const formData = new URLSearchParams();
            formData.append('memoId', memoId);

            fetch('api/memos/delete', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if(data.success) loadMemos();
            });
        }
    </script>
</body>
</html>
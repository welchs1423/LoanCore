<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 상세 정보</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="mb-4"><i class="fa-solid fa-file-invoice"></i> 대출 상세 정보</h2>
        
        <c:choose>
            <c:when test="${empty app}">
                <div class="alert alert-warning"><i class="fa-solid fa-triangle-exclamation"></i> 해당 신청 내역을 찾을 수 없습니다.</div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-md-7">
                        <div id="pdfArea" class="card p-4 shadow-sm mb-4 position-relative bg-white border-0">
                            <div class="position-absolute top-0 end-0 p-3">
                                <img src="api/qr?appId=${app.applicationId}" alt="QR Code" class="img-thumbnail" style="width: 90px; height: 90px;">
                            </div>
                            <h5 class="border-bottom pb-2 mb-3 w-75 fw-bold text-secondary">신청 내역</h5>
                            <table class="table table-borderless">
                                <tr><th style="width: 150px;"><i class="fa-solid fa-hashtag text-muted"></i> 신청 번호</th><td id="appId">${app.applicationId}</td></tr>
                                <tr><th><i class="fa-solid fa-user text-muted"></i> 고객 ID</th><td class="fw-bold">${app.customerId}</td></tr>
                                <tr><th><i class="fa-solid fa-map-location-dot text-muted"></i> 거주지 주소</th><td>${not empty app.address ? app.address : '<span class="text-muted">입력된 주소 없음</span>'}</td></tr>
                                <tr><th><i class="fa-solid fa-sack-dollar text-muted"></i> 신청 금액</th><td><span class="text-primary fw-bold">${app.amount} 원</span></td></tr>
                                <tr>
                                    <th><i class="fa-solid fa-signal text-muted"></i> 진행 상태</th>
                                    <td>
                                        <c:if test="${app.statusCode == 'APPROVE'}"><span class="badge bg-success">승인</span></c:if>
                                        <c:if test="${app.statusCode == 'REJECT'}"><span class="badge bg-danger">거절</span></c:if>
                                        <c:if test="${app.statusCode == 'PENDING'}"><span class="badge bg-warning">대기</span></c:if>
                                    </td>
                                </tr>
                                <tr data-html2canvas-ignore="true">
                                    <th><i class="fa-solid fa-paperclip text-muted"></i> 증빙 서류</th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty app.fileName}">
                                                <a href="download?fileName=${app.fileName}" class="btn btn-sm btn-outline-primary"><i class="fa-solid fa-download"></i> 다운로드</a>
                                                <span class="text-muted ms-2">${app.fileName}</span>
                                            </c:when>
                                            <c:otherwise><span class="text-muted">첨부파일 없음</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        
                        <div class="d-flex gap-2 flex-wrap mb-4">
                            <a href="list" class="btn btn-primary"><i class="fa-solid fa-list"></i> 목록으로</a>
                            <a href="edit?id=${app.applicationId}" class="btn btn-warning text-white"><i class="fa-solid fa-pen"></i> 수정</a>
                            <form action="delete" method="post" onsubmit="return confirm('정말 이 신청 건을 취소하시겠습니까?');">
                                <input type="hidden" name="id" value="${app.applicationId}">
                                <button type="submit" class="btn btn-danger"><i class="fa-solid fa-trash"></i> 삭제</button>
                            </form>
                            <c:if test="${app.statusCode == 'APPROVE'}">
                                <button type="button" class="btn btn-success" onclick="exportPDF()"><i class="fa-solid fa-file-pdf"></i> PDF 발급</button>
                            </c:if>
                            <a href="./" class="btn btn-secondary"><i class="fa-solid fa-house"></i> 홈으로</a>
                        </div>
                    </div>

                    <div class="col-md-5">
                        <div class="card shadow-sm h-100 mb-4 border-0">
                            <div class="card-header bg-white border-bottom fw-bold text-primary"><i class="fa-solid fa-comments"></i> 심사 메모</div>
                            <div class="card-body d-flex flex-column bg-light">
                                <div id="memoList" class="flex-grow-1 overflow-auto mb-3 px-2" style="max-height: 400px;">
                                </div>
                                <div class="input-group mt-auto shadow-sm">
                                    <input type="text" id="memoInput" class="form-control border-0" placeholder="메모를 입력하세요..." onkeypress="if(event.keyCode==13) addMemo();">
                                    <button class="btn btn-primary border-0" type="button" onclick="addMemo()"><i class="fa-solid fa-paper-plane"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="liveToast" class="toast align-items-center text-bg-dark border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body" id="toastMessage"></div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>

    <script>
        const appId = document.getElementById('appId') ? document.getElementById('appId').innerText : null;

        document.addEventListener("DOMContentLoaded", function() {
            if(appId) loadMemos();
        });

        function showToast(message) {
            document.getElementById('toastMessage').innerHTML = '<i class="fa-solid fa-circle-info me-2"></i>' + message;
            const toast = new bootstrap.Toast(document.getElementById('liveToast'));
            toast.show();
        }

        function loadMemos() {
            fetch('api/memos?applicationId=' + appId)
                .then(response => response.json())
                .then(data => {
                    const listDiv = document.getElementById('memoList');
                    listDiv.innerHTML = '';
                    if(data.length === 0) {
                        listDiv.innerHTML = '<p class="text-muted text-center mt-4"><i class="fa-regular fa-comment-dots fs-2 d-block mb-2"></i>등록된 메모가 없습니다.</p>';
                        return;
                    }
                    data.forEach(memo => {
                        const date = new Date(memo.createdAt).toLocaleString();
                        listDiv.innerHTML += 
                            '<div class="bg-white rounded-3 p-3 mb-2 shadow-sm border border-light">' +
                                '<div class="d-flex justify-content-between align-items-center mb-2">' +
                                    '<strong class="text-dark"><i class="fa-solid fa-user-pen text-secondary me-1"></i>' + memo.writer + '</strong>' +
                                    '<div>' +
                                        '<small class="text-muted me-2" style="font-size: 0.75rem;">' + date + '</small>' +
                                        '<button class="btn btn-sm text-danger py-0 px-1 border-0" onclick="deleteMemo(' + memo.memoId + ')"><i class="fa-solid fa-xmark"></i></button>' +
                                    '</div>' +
                                '</div>' +
                                '<span class="text-secondary">' + memo.content + '</span>' +
                            '</div>';
                    });
                });
        }

        function addMemo() {
            const content = document.getElementById('memoInput').value;
            if(!content.trim()) {
                showToast("내용을 입력하세요.");
                return;
            }

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
                    showToast("메모가 정상적으로 등록되었습니다.");
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
                if(data.success) {
                    loadMemos();
                    showToast("메모가 삭제되었습니다.");
                }
            });
        }

        function exportPDF() {
            const element = document.getElementById('pdfArea');
            const title = document.createElement('h2');
            title.innerHTML = '대출 승인 확인서';
            title.className = 'text-center mb-4 fw-bold';
            element.insertBefore(title, element.firstChild);

            const opt = {
                margin:       0.5,
                filename:     '대출승인확인서_' + appId + '.pdf',
                image:        { type: 'jpeg', quality: 0.98 },
                html2canvas:  { scale: 2, useCORS: true },
                jsPDF:        { unit: 'in', format: 'a4', orientation: 'portrait' }
            };

            html2pdf().set(opt).from(element).save().then(() => {
                element.removeChild(title);
            });
        }
    </script>
</body>
</html>
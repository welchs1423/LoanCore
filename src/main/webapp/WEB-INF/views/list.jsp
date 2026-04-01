<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 신청 내역 조회</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>대출 신청 내역 조회</h2>
            <a href="./" class="btn btn-outline-secondary">메인으로</a>
        </div>

        <div class="card p-4 shadow-sm mb-4">
            <div class="row align-items-end">
                <div class="col-md-6">
                    <form action="list" method="get" class="d-flex gap-2">
                        <input type="text" name="keyword" class="form-control" placeholder="고객 ID 검색" value="${keyword}">
                        <select name="status" class="form-select" style="width: 150px;">
                            <option value="">전체 상태</option>
                            <option value="APPROVE" ${status == 'APPROVE' ? 'selected' : ''}>승인</option>
                            <option value="REJECT" ${status == 'REJECT' ? 'selected' : ''}>거절</option>
                            <option value="PENDING" ${status == 'PENDING' ? 'selected' : ''}>대기</option>
                        </select>
                        <button type="submit" class="btn btn-dark">검색</button>
                    </form>
                </div>
                <div class="col-md-6 d-flex justify-content-end gap-2 mt-3 mt-md-0">
                    <form action="excel/upload" method="post" enctype="multipart/form-data" class="d-flex gap-2">
                        <input type="file" name="excelFile" class="form-control form-control-sm" accept=".xlsx, .xls" required>
                        <button type="submit" class="btn btn-sm btn-success text-nowrap">엑셀 업로드</button>
                    </form>
                    <a href="excel" class="btn btn-sm btn-outline-success text-nowrap">엑셀 다운로드</a>
                </div>
            </div>
        </div>

        <form action="bulk-update" method="post" id="bulkForm">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <div class="d-flex gap-2">
                    <select name="bulkStatus" class="form-select form-select-sm" style="width: 130px;" required>
                        <option value="">상태 일괄 변경</option>
                        <option value="APPROVE">승인으로 변경</option>
                        <option value="REJECT">거절로 변경</option>
                        <option value="PENDING">대기로 변경</option>
                    </select>
                    <button type="submit" class="btn btn-sm btn-primary" onclick="return confirmBulkUpdate()">적용</button>
                </div>
            </div>

            <div class="card shadow-sm bg-white">
                <table class="table table-hover mb-0 text-center align-middle">
                    <thead class="table-light">
                        <tr>
                            <th style="width: 50px;">
                                <input class="form-check-input" type="checkbox" id="selectAll" onclick="toggleAll(this)">
                            </th>
                            <th>신청 번호</th>
                            <th>고객 ID</th>
                            <th>신청 금액</th>
                            <th>상태</th>
                            <th>상세</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty loanList}">
                                <tr><td colspan="6" class="py-4 text-muted">등록된 대출 신청 내역이 없습니다.</td></tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="app" items="${loanList}">
                                    <tr>
                                        <td>
                                            <input class="form-check-input row-checkbox" type="checkbox" name="appIds" value="${app.applicationId}">
                                        </td>
                                        <td>${app.applicationId}</td>
                                        <td class="fw-bold">${app.customerId}</td>
                                        <td><fmt:formatNumber value="${app.amount}" pattern="#,###"/> 원</td>
                                        <td>
                                            <c:if test="${app.statusCode == 'APPROVE'}"><span class="badge bg-success">승인</span></c:if>
                                            <c:if test="${app.statusCode == 'REJECT'}"><span class="badge bg-danger">거절</span></c:if>
                                            <c:if test="${app.statusCode == 'PENDING'}"><span class="badge bg-warning">대기</span></c:if>
                                        </td>
                                        <td>
                                            <a href="detail?id=${app.applicationId}" class="btn btn-sm btn-outline-primary">조회</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </form>

        <nav class="mt-4">
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="list?page=${i}&keyword=${keyword}&status=${status}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>

    <script>
        function toggleAll(source) {
            const checkboxes = document.querySelectorAll('.row-checkbox');
            checkboxes.forEach(checkbox => checkbox.checked = source.checked);
        }

        function confirmBulkUpdate() {
            const checkedCount = document.querySelectorAll('.row-checkbox:checked').length;
            const statusSelect = document.querySelector('select[name="bulkStatus"]').value;
            
            if (statusSelect === "") {
                alert("변경할 상태를 선택해주세요.");
                return false;
            }
            if (checkedCount === 0) {
                alert("처리할 항목을 하나 이상 선택해주세요.");
                return false;
            }
            return confirm(checkedCount + "건의 대출 상태를 일괄 변경하시겠습니까?");
        }
    </script>
</body>
</html>
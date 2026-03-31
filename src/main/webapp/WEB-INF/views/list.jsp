<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 신청 내역 조회</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">대출 신청 내역 목록</h2>
        
        <div class="d-flex justify-content-between mb-3">
            <form action="list" method="get" class="d-flex gap-2 w-75">
                <select name="status" class="form-select w-25">
                    <option value="">전체 상태</option>
                    <option value="APPROVE" ${status == 'APPROVE' ? 'selected' : ''}>승인</option>
                    <option value="REJECT" ${status == 'REJECT' ? 'selected' : ''}>거절</option>
                    <option value="PENDING" ${status == 'PENDING' ? 'selected' : ''}>대기</option>
                </select>
                <input type="text" name="keyword" class="form-control w-25" placeholder="고객 ID 검색" value="${keyword}">
                <button type="submit" class="btn btn-primary">검색</button>
                <a href="list" class="btn btn-outline-secondary">초기화</a>
            </form>
            <a href="excel" class="btn btn-success">엑셀 다운로드</a>
        </div>

        <table class="table table-hover table-bordered shadow-sm">
            <thead class="table-light">
                <tr>
                    <th>신청 번호</th>
                    <th>고객 ID</th>
                    <th>신청 금액 (원)</th>
                    <th>진행 상태</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty loanList}">
                        <tr>
                            <td colspan="4" class="text-center">신청 내역이 없습니다.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="app" items="${loanList}">
                            <tr>
                                <td><a href="detail?id=${app.applicationId}">${app.applicationId}</a></td>
                                <td>${app.customerId}</td>
                                <td>${app.amount}</td>
                                <td>
                                    <c:if test="${app.statusCode == 'APPROVE'}">
                                        <span class="badge bg-success">승인</span>
                                    </c:if>
                                    <c:if test="${app.statusCode == 'REJECT'}">
                                        <span class="badge bg-danger">거절</span>
                                    </c:if>
                                    <c:if test="${app.statusCode == 'PENDING'}">
                                        <span class="badge bg-warning">대기</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <c:if test="${empty keyword and empty status and totalPages > 1}">
            <nav>
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="list?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>

        <div class="mt-3">
            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
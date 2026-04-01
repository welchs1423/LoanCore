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
                <div class="card p-4 shadow-sm">
                    <table class="table table-borderless">
                        <tr>
                            <th style="width: 150px;">신청 번호</th>
                            <td>${app.applicationId}</td>
                        </tr>
                        <tr>
                            <th>고객 ID</th>
                            <td>${app.customerId}</td>
                        </tr>
                        <tr>
                            <th>거주지 주소</th>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty app.address}">
                                        ${app.address}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">입력된 주소 없음</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>신청 금액</th>
                            <td>${app.amount} 원</td>
                        </tr>
                        <tr>
                            <th>진행 상태</th>
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
                        <tr>
                            <th>증빙 서류</th>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty app.fileName}">
                                        <a href="download?fileName=${app.fileName}" class="btn btn-sm btn-outline-primary">파일 다운로드</a>
                                        <span class="text-muted ms-2">${app.fileName}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">첨부파일 없음</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
        
        <div class="mt-3 d-flex gap-2">
            <a href="list" class="btn btn-primary">목록으로</a>
            <a href="edit?id=${app.applicationId}" class="btn btn-warning">정보 수정</a>
            <form action="delete" method="post" onsubmit="return confirm('정말 이 신청 건을 취소하시겠습니까?');">
                <input type="hidden" name="id" value="${app.applicationId}">
                <button type="submit" class="btn btn-danger">신청 취소</button>
            </form>
            <a href="./" class="btn btn-secondary">메인으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
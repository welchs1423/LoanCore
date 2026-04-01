<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>대출 심사 대시보드</title>
    <style>
        body { font-family: sans-serif; margin: 40px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }
        th { background-color: #f4f4f4; }
        .btn { display: inline-block; padding: 10px 15px; background-color: #007BFF; color: white; text-decoration: none; border-radius: 5px; margin-bottom: 20px;}
        .stat-box { padding: 15px; background-color: #e9ecef; border-radius: 5px; display: inline-block; margin-bottom: 20px; }
    </style>
</head>
<body>
    <h1>대출 심사 대시보드</h1>
    
    <div class="stat-box">
        <h2>심사 현황 통계</h2>
        <p style="font-size: 18px;">총 신청 건수: <strong>${totalCount}</strong> 건</p>
    </div>
    
    <br>
    <a href="apply" class="btn">새 대출 신청하기</a>
    <a href="audit" class="btn" style="background-color: #343a40;">감사 로그 모니터링</a>

    <h3>최근 신청 내역</h3>
    <table>
        <thead>
            <tr>
                <th>신청 ID</th>
                <th>고객 ID</th>
                <th>신청 금액</th>
                <th>상태</th>
                <th>신청 일시</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty applications}">
                    <tr>
                        <td colspan="5">신청된 대출 내역이 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="app" items="${applications}">
                        <tr>
                            <td><a href="detail/${app.applicationId}" style="font-weight:bold; color:blue;">${app.applicationId}</a></td>
                            <td>${app.customerId}</td>
                            <td>${app.amount}</td>
                            <td style="color: ${app.statusCode == 'APPROVED' ? 'green' : 'red'}; font-weight: bold;">
                                ${app.statusCode}
                            </td>
                            <td>${app.appliedAt}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
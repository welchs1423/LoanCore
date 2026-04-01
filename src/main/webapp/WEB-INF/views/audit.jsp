<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>시스템 감사 로그 대시보드</title>
    <style>
        body { font-family: sans-serif; margin: 40px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #343a40; color: white; }
        .btn-back { display: inline-block; padding: 10px 15px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; margin-bottom: 20px; }
        .log-action { font-weight: bold; color: #007bff; }
    </style>
</head>
<body>
    <h1>시스템 감사 로그 (Audit Log)</h1>
    
    <a href="/LoanCore/" class="btn-back">메인 대시보드로 돌아가기</a>

    <table>
        <thead>
            <tr>
                <th>로그 ID</th>
                <th>작업자 (Admin)</th>
                <th>수행 작업 (Action)</th>
                <th>대상 (Target ID)</th>
                <th>발생 일시 (Timestamp)</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty auditLogs}">
                    <tr>
                        <td colspan="5" style="text-align:center;">기록된 감사 로그가 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="log" items="${auditLogs}">
                        <tr>
                            <td>${log.logId}</td>
                            <td>${log.adminId}</td>
                            <td class="log-action">${log.actionType}</td>
                            <td>${log.targetId}</td>
                            <td>${log.createdAt}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
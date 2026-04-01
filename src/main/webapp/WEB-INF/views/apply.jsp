<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 대출 신청</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2>대출 신청</h2>

        <c:if test="${not empty errors}">
            <div class="alert alert-danger">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error.defaultMessage}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form:form action="submit-loan" method="post" enctype="multipart/form-data" modelAttribute="app">
            <div class="mb-3">
                <label for="customerId" class="form-label">고객 ID</label>
                <form:input path="customerId" class="form-control" id="customerId" />
                <form:errors path="customerId" cssClass="text-danger" />
            </div>
            
            <div class="mb-3">
                <label for="amount" class="form-label">신청 금액 (원)</label>
                <form:input path="amount" type="number" class="form-control" id="amount" />
                <form:errors path="amount" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <label for="address" class="form-label">거주지 주소</label>
                <form:input path="address" class="form-control" id="address" />
                <form:errors path="address" cssClass="text-danger" />
            </div>

            <div class="mb-3">
                <label for="uploadFile" class="form-label">증빙 서류 (선택)</label>
                <input type="file" class="form-control" id="uploadFile" name="uploadFile">
            </div>

            <button type="submit" class="btn btn-primary">신청하기</button>
            <a href="./" class="btn btn-secondary">취소</a>
        </form:form>
    </div>
</body>
</html>
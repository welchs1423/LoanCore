<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LoanCore - 여신 시스템</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="p-5 mb-4 bg-white rounded-3 shadow-sm text-center">
            <h1 class="display-5 fw-bold">LoanCore 여신 심사 시스템</h1>
            <p class="col-md-8 mx-auto fs-4">빠르고 정확한 대출 심사 프로세스</p>
            <div class="d-flex justify-content-center gap-3 mt-4">
                <a href="apply" class="btn btn-primary btn-lg">대출 신청하기</a>
                <a href="list" class="btn btn-outline-secondary btn-lg">신청 내역 조회</a>
            </div>
        </div>

        <div class="row text-center mb-5">
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <div class="card-body py-4">
                        <h5 class="card-title text-muted">총 신청 건수</h5>
                        <h2 class="display-6 fw-bold text-dark"><c:out value="${totalCount != null ? totalCount : 0}"/>건</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <div class="card-body py-4">
                        <h5 class="card-title text-muted">심사 승인</h5>
                        <h2 class="display-6 fw-bold text-success"><c:out value="${approveCount != null ? approveCount : 0}"/>건</h2>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <div class="card-body py-4">
                        <h5 class="card-title text-muted">심사 거절</h5>
                        <h2 class="display-6 fw-bold text-danger"><c:out value="${rejectCount != null ? rejectCount : 0}"/>건</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
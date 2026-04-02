<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="memo-section">
    <h3><spring:message code="memo.title" /></h3>
    
    <div class="memo-input-group">
        <input type="text" id="memoInput" placeholder="<spring:message code='memo.placeholder' />">
        <button type="button" class="btn btn-primary" onclick="addMemo()">
            <spring:message code="memo.btn.add" />
        </button>
    </div>

    <div id="memoList">
        <p class="empty-msg"><spring:message code="memo.empty" /></p>
    </div>
</div>
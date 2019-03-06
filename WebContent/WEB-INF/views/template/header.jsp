<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header-box container">
    <!-- S: row -->
    <div class="row">
        <h1 class="col-sm-3 col-xs-12">
            <a href="${pageContext.request.contextPath}/main/main.do">Scheduler</a>
        </h1>
        <ul class="nav col-sm-8 col-xs-12 row">
            <li class="col-xs-2"><a href="${pageContext.request.contextPath}/main/main.do">달력보기</a></li>
            <li class="col-xs-2"><a href="${pageContext.request.contextPath}/sc/addSchedule.do">일정추가</a></li>
            <li class="col-xs-2"><a href="${pageContext.request.contextPath}/sc/commingSchedule.do">다가올 일정</a></li>
            <li class="col-xs-2"><a href="${pageContext.request.contextPath}/sc/passedSchedule.do">지난 일정</a></li>
            <li class="col-xs-2"><a href="${pageContext.request.contextPath}/record/diaryCal.do">다이어리</a></li>
            <li class="col-xs-2"><a href="${pageContext.request.contextPath}/record/gallery.do">갤러리</a></li>
        </ul>
    </div>
    <!-- E: row -->
</div>
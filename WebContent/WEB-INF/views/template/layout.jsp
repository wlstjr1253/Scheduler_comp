<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--============view page 설정============ --%>
<c:set var="viewPage">
	<jsp:include page="${viewURI}"></jsp:include>
</c:set>
<%--============view page 설정============ --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Scheduler</title>
    <link rel="apple-touch-icon" sizes="57x57" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/imgs/favicon/apple-icon-180x180.png">
	<link rel="icon" type="image/png" sizes="192x192"  href="${pageContext.request.contextPath}/imgs/favicon/android-icon-192x192.png">
	<link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/imgs/favicon/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="${pageContext.request.contextPath}/imgs/favicon/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/imgs/favicon/favicon-16x16.png">
	<link rel="manifest" href="${pageContext.request.contextPath}/imgs/favicon/manifest.json">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/imgs/favicon/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/all.css">
    <link rel="stylesheet" href="../css/material-design-iconic-font.css">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote.js"></script>
    <script src="${pageContext.request.contextPath}/js/handlebars-v4.1.0.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.color.js"></script>
</head>
<body>
	<div id="main">
		<%-- S: header --%>
		<c:if test="${user_id != null && user_auth > 0}">
		<div class="header">
			<jsp:include page="header.jsp" />
		</div>
		</c:if>
		<%-- E: header --%>
		
		<%-- S: left --%>
		<c:if test="${user_id != null && user_auth > 0}">
		<aside class="left">
			<jsp:include page="left.jsp" />
		</aside>
		</c:if>
		<%-- E: left --%>
		
		<%-- S: main --%>
		${viewPage}
		<%-- E: main --%>
		
		<div class="footer" class="login">
			<jsp:include page="footer.jsp" />
		</div>
	</div>
	
	<div class="al-modal"></div>
	
	<script id="alarmWindow" type="text/x-handlebars-template">
	{{#list}}
	<div class="alarm-window">
		<div class="content">
			<a href="${pageContext.request.contextPath}/sc/detailSchedule.do?sc_idx={{sc_idx}}">
				<h3>
					<span class="title">{{sc_title}}</span>
					일정을 확인하세요!
				</h3>
			</a>
		</div>
		<div class="window-footer">
			<label>
				<span>다시 보지 않기</span>
				<input type="checkbox">
			</label>
			<button type="button" class="btn btn-basic btn-close" data-alidx="{{al_idx}}">닫기</button>
		</div>
	</div>
	{{/list}}
	</script>
	
	<script src="../js/app.js"></script>
	<script src="../js/ajaxAction/summary.js"></script>
	<script src="../js/ajaxAction/favorite.js"></script>
	<script src="../js/ajaxAction/alarm.js"></script>
</body>
</html>
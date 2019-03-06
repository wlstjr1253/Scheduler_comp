<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${check && user_auth == 0}">
	<script>
		alert('탈퇴한 회원 입니다.');
		history.go(-1);
	</script>
</c:if>
<c:if test="${check && user_auth > 0}">
	<script>
		location.href='${pageContext.request.contextPath}/main/main.do';
	</script>
</c:if>
<c:if test="${!check}">
	<script>
		alert('아이디 또는 비밀번호가 불일치 합니다.');
		history.go(-1);
	</script>
</c:if>
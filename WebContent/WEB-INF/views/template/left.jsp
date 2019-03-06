<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="snb-btn">
	<a href="#"><i class="zmdi zmdi-menu"></i></a>
</div>
<div class="user-info">
	<img class="user-img" src="../imgs/profile-pics/8.jpg" alt>
	<div>
		<p class="user-name">${user_name}</p>
		<p class="user-email">${user_email}</p>
	</div>
</div>
<ul class="snb">
	<li><a href="${pageContext.request.contextPath}/main/main.do"><i class="zmdi zmdi-home"></i>HOME</a></li>
	<li><a href="#" data-toggle="modal" data-target=".logout-modal"><i class="zmdi zmdi-account-box"></i>로그아웃</a>
	</li>
	<li><a href="${pageContext.request.contextPath}/main/mypage.do"><i class="zmdi zmdi-settings"></i>마이페이지</a></li>
	<li><a href="${pageContext.request.contextPath}/record/favorite.do"><i
			class="zmdi zmdi-folder-star-alt"></i>즐겨찾기</a></li>
	<li><a href="${pageContext.request.contextPath}/sc/commingSchedule.do"><i
			class="zmdi zmdi-filter-list"></i>일정이 곧 다가와요!</a>
		<ul class="comming-list">
		</ul>
	</li>
</ul>

<script>
function logoutCheck() {
	  alert('로그아웃 되었습니다.');
	  location.href = '${pageContext.request.contextPath}/main/logout.do';
}
</script>

<!-- S: warn-modal -->
<div class="modal fade warn-modal logout-modal" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header align-center">
				<h5 class="modal-title flex1">로그아웃 하시겠습니까?</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<!-- <div class="modal-body">
				<p>로그아웃 하시겠습니까?</p>
			</div> -->
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-danger" onclick="logoutCheck();">로그아웃</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
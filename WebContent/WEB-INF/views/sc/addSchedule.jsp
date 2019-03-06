<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/wickedpicker.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/datepicker.min.css">
<script src="${pageContext.request.contextPath}/js/wickedpicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/js/i18n/datepicker.ko.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.css">
<script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- main -->
<div class="main add-schedule">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<c:if test="${empty schedule}">
			<jsp:include page="add.form.jsp" />
			</c:if>
			
			<c:if test="${!empty schedule}">
			<jsp:include page="detail.form.jsp" />
			</c:if>
		</div>
		<!-- E: content -->
	</div>
	<!-- E: bg -->
</div>

<!-- S: modal -->
<div class="modal fade add-member-modal" id="add-member-modal"
	role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">추가 멤버 검색</h5>
			</div>

			<!-- modal-body -->
			<div class="modal-body">
				<!-- S: search-box -->
				<div class="search-box">
					<label for="member"> <span class="sr-only">회원검색</span> <span
							class="icon"> <i class="fas fa-search"></i>
						</span> <input type="text" placeholder="검색할 회원 이름, 이메일을 입력하세요.">
					</label>
				</div>
				<!-- E: search-box -->

				<!-- S: result-box -->
				<div class="result-box">
					<div class="ajax-loading">
						<img alt="로딩중" src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg">
					</div>
					<%-- <li>
							<div>
								<p class="member-name">캡틴아메리카</p>
								<p class="member-mail">captain@avengers.com</p>
							</div> <a href="#" class="btn btn-default">추가 <span class="icon"><i
									class="fas fa-plus-square"></i></i></span></a>
						</li>
						<li>
							<div>
								<p class="member-name">캡틴아메리카</p>
								<p class="member-mail">captain@avengers.com</p>
							</div> <a href="#" class="btn btn-default">추가 <span class="icon"><i
									class="fas fa-plus-square"></i></i></span></a>
						</li>
						<li>
							<div>
								<p class="member-name">캡틴아메리카</p>
								<p class="member-mail">captain@avengers.com</p>
							</div> <a href="#" class="btn btn-default">추가 <span class="icon"><i
									class="fas fa-plus-square"></i></i></span></a>
						</li>
						<li>
							<div>
								<p class="member-name">캡틴아메리카</p>
								<p class="member-mail">captain@avengers.com</p>
							</div> <a href="#" class="btn btn-default">추가 <span class="icon"><i
									class="fas fa-plus-square"></i></i></span></a>
						</li>
						<li>
							<div>
								<p class="member-name">캡틴아메리카</p>
								<p class="member-mail">captain@avengers.com</p>
							</div> <a href="#" class="btn btn-default">추가 <span class="icon"><i
									class="fas fa-plus-square"></i></i></span></a>
						</li> --%>
					<ul class="search-result">
						
					</ul>
				</div>
				<!-- E: result-box -->
			</div>

			<!-- modal-footer -->
			<div class="modal-footer">
				<button type="button" class="btn-modal-basic" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>
<!-- E: modal -->


<!-- S: modal -->
<div class="modal fade" id="back_warn_modal" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">뒤로 가시겠습니까?</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>

			<!-- modal-body -->
			<div class="modal-body">
				<p>뒤로 돌아가시면 작성한 내용이 모두 삭제 됩니다</p>
				<p>뒤로 가시겠습니까?</p>
			</div>

			<!-- modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary history-back-btn">뒤로가기</button>
			</div>
		</div>
	</div>
</div>
<!-- E: modal -->

<!-- S: warn-modal -->
<div class="modal fade warn-modal del-member" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">삭제 하시겠습니까?</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p><span class="member-name"></span> <span class="member-mail"></span>를 공유 회원에서 제거 합니다.</p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-danger confirm-remove">삭제</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: warn-modal -->

<script>
	$('.add-member-modal .result-box').mCustomScrollbar();
	$('.member section .shared-list').mCustomScrollbar();
</script>

<script id="userList" type="text/x-handlebars-template">
	{{#users}}
	<li>
		<div>
			<p class="member-name">{{name}}</p>
			<p class="member-mail">{{email}}</p>
		</div> <a href="#" class="btn btn-default invite-btn" data-id={{id}}>추가 <span class="icon"><i
				class="fas fa-plus-square"></i></i></span></a>
	</li>
	{{/users}}
</script>

<script src="${pageContext.request.contextPath}/js/addSchedule.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxAction/searchMember.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxAction/shareMember.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/jquery.mask.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/js/additional-methods.js"></script>
<script src="${pageContext.request.contextPath}/js/localization/messages_ko.js"></script>
<!-- main -->
<div class="main join">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<img src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg"
				id="loading" style="display: none">
			<form id="modify_form">
				<fieldset>
					<legend>
						수정하기 <span class="icon"><i class="fas fa-edit"></i></span>
					</legend>
					<ul>
						<li class="form-group no-modify"><label> <span class="title">이름</span>
								<input type="hidden" class="required form-control" required
								aria-required="true" name="userName" maxlength="10" value="${user.name}">
								<p>${user.name }</p> 
						</label></li>
						<li class="form-group no-modify"><label> <span class="title">아이디</span>
								<input type="hidden" class="required form-control" required
								aria-required="true" name="userId" maxlength="10" value="${user.id}">
								<p>${user.id}</p> 
						</label></li>
						<li class="form-group"><label> <span class="title">비밀번호</span>
								<input type="password" class="required form-control" required
								aria-required="true" name="pwd" maxlength="10"> <i
								class="form-group__bar"></i>
						</label></li>
						<li class="form-group"><label> <span class="title">비밀번호
									확인</span> <input type="password" class="required form-control"
								required aria-required="true" name="pwd2" maxlength="10">
								<i class="form-group__bar"></i>
						</label></li>
						<li class="form-group"><label> <span class="title">핸드폰
									번호</span> <input type="tel" name="phone" class="form-control phone"
								maxlength="9" value="${user.phone}"> <i class="form-group__bar"></i>
						</label></li>
						<li class="form-group no-modify"><label> <span class="title">이메일</span>
								<input type="text" name="email" class="required form-control"
								required aria-required="true" value="${user.email}">
								<%-- <p>${user.email}</p> --%>
						</label></li>
						<li class="align-right member-out"><a href="#"
							data-target=".bye-modal" data-toggle="modal">회원탈퇴</a></li>
					</ul>
					<ul class="btn-list align-center">
						<li><a href="#" onclick="history.go(-1);return false;">뒤로가기</a></li>
						<li><input type="submit" value="수정하기" class="submit-input"></li>
					</ul>
				</fieldset>
			</form>
		</div>
		<!-- E: content -->
	</div>
	<!-- E: bg -->
</div>


<!-- S: modal -->
<div class="modal fade" id="back_warn_modal" role="dialog">
	<div class="modal-dialog">
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


<!-- S: modal -->
<div class="modal fade confirm-modify" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- modal-header -->
			<div class="modal-header align-center">
				<h5 class="modal-title flex1">수정 되었습니다.</h5>
			</div>

			<!-- modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-default" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">확인</button>
			</div>
		</div>
	</div>
</div>
<!-- E: modal -->


<!-- S: modal -->
<div class="modal fade bye-modal" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">탈퇴 하시겠습니까?</h5>
			</div>

			<!-- modal-body -->
			<div class="modal-body">
				
				<label> <span class="title">비밀번호 확인</span> <input
					type="password" name="passwd">
				</label>

				<div class="btn-list align-center">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
					<button type="submit" class="btn btn-danger">탈퇴</button>
				</div>

			</div>
		</div>
	</div>
</div>
<!-- E: modal -->

<script>
	$('.phone').mask('000-0000-0000');
	$('form').validate();
</script>
<script src="${pageContext.request.contextPath}/js/modifyUser.js"></script>
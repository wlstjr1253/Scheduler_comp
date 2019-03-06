<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.note-editor .note-table,
.note-editor .note-insert {display: none;}
</style>
<script>
    $(document).ready(function(){
        $('#summernote').summernote({
            placeholder: '일기를 작성해 주세요.',
            height: 300
        });
    })
</script>
<!-- main -->
<div class="main diary">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<c:if test="${empty diary}">
			<jsp:include page="writeDiary.jsp" />
			</c:if>
			
			<c:if test="${!empty diary}">
			<jsp:include page="readDiary.jsp" />
			</c:if>
		</div>
		<!-- E: content -->
	</div>
	<!-- E: bg -->
</div>


<!-- S: warn-modal -->
<div class="modal fade warn-modal back-warn" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">뒤로 가시겠습니까?</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p></p>
				<p class="text-warn">경고! 작성한 내용이 모두 삭제 됩니다.</p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-danger" onclick="location.href='diaryCal.do'">뒤로가기</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: warn-modal -->

<!-- S: warn-modal -->
<div class="modal fade warn-modal fav-warn" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">즐겨찾기에 추가 하시겠습니까?</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p>즐겨찾기 모음에 일기를 등록 합니다.</p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary add-fav-diary">추가</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: warn-modal -->


<!-- S: warn-modal -->
<div class="modal fade warn-modal del-warn" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">일기 삭제</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p>일기를 삭제 합니다.</p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-danger">삭제</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: warn-modal -->

<!-- S: complete-modal -->
<div class="modal fade complete-modal" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">

			<!-- S: modal-body -->
			<div class="modal-body">
				<p class="message"></p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-primary"
					onclick="location.replace('./diaryCal.do');">확인</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: complete-modal -->

<script src="${pageContext.request.contextPath}/js/ajaxAction/diaryAdd.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxAction/diaryModify.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxAction/diaryDelete.js"></script>
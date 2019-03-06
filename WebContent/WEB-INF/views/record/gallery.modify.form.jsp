<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form action="./galleryModifyAjaxAction.do" method="post"
	enctype="multipart/form-data" class="modify-form" id="modifyGallery">
	<fieldset>
		<legend>앨범 보기</legend>
		<input type="hidden" value="${galleryItem.g_idx}" name="g_idx">
		<div class="picture">
			<!-- 등록된 사진이 보여 집니다. -->
			<img
				src="${pageContext.request.contextPath}/upload/${galleryItem.g_photo1}"
				alt>
		</div>
		<div class="file modify-file">
			<label for="filename" class="btn btn-danger">파일선택</label> <input
				type="file" id="filename" accept="image/*" style="display: none">
			<p class="file-path">${galleryItem.g_photo1}</p>
		</div>
		<div class="title">
			<input type="text" required name="g_title" maxlength="25"
				value="${galleryItem.g_title}">
		</div>
		<div class="diary-content">
			<textarea name="editordata" id="summernote" cols="30" rows="10"
				placeholder="일기쓰기">${galleryItem.g_content}</textarea>
		</div>
		<ul class="btn-list">
			<li><a href="#" class="btn btn-basic" onclick="history.go(-1);return false;">뒤로가기</a></li>
			<li><a href="#" class="btn btn-success" data-toggle="modal"
				data-target=".fav-warn">즐겨찾기</a></li>
			<li><a href="#" class="btn btn-danger" data-toggle="modal"
				data-target=".del-warn">삭제하기</a></li>
			<li><input type="submit" value="수정하기" class="btn btn-primary"></li>
		</ul>
	</fieldset>
</form>

<!-- S: fav-warn -->
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
				<p>즐겨찾기 모음에 갤러리를 등록 합니다.</p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary add-fav-gallery">추가</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: fav-warn -->


<!-- S: warn-modal -->
<div class="modal fade warn-modal del-warn" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">갤러리 삭제</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p>갤러리를 삭제 합니다.</p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-danger btn-remove">삭제</button>
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
					onclick="location.replace('./gallery.do');">확인</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: complete-modal -->

<script	src="${pageContext.request.contextPath}/js/ajaxAction/galleryModify.js"></script>
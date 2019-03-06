<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="modify-diary">
	<fieldset>
		<legend>작성된 일기</legend>
		<input type="hidden" name="d_idx" value="${diary.d_idx}">
		<div class="title">
			<input type="text" placeholder="제목을 입력 하세요." name="d_title" required value="${diary.d_title}">
		</div>
		<div class="diary-content">
			<!-- summer note가 들어 갑니다. -->
			<textarea name="d_content" id="summernote" cols="30" rows="10"
				placeholder="일기쓰기">${diary.d_content}</textarea>
		</div>
		<ul class="btn-list">
			<li><a href="#" class="btn btn-basic" data-toggle="modal"
				data-target=".back-warn">뒤로가기</a></li>
			<li><a href="#" class="btn btn-success" data-toggle="modal"
				data-target=".fav-warn">즐겨찾기</a></li>
			<li><a href="#" class="btn btn-danger" data-toggle="modal"
				data-target=".del-warn">삭제하기</a></li>
			<li><input type="submit" value="수정하기" class="btn btn-primary"></li>
		</ul>
	</fieldset>
</form>
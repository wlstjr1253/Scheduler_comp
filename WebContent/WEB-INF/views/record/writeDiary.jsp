<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="write-diary">
	<fieldset>
		<legend>일기 쓰기</legend>
		<div class="title">
			<input type="text" placeholder="제목을 입력 하세요." name="d_title" required maxlength="30">
		</div>
		<div class="diary-content">
			<!-- summer note가 들어 갑니다. -->
			<textarea name="d_content" id="summernote" cols="30" rows="10"
				placeholder="일기쓰기"></textarea>
		</div>
		<ul class="btn-list">
			<li><a href="#" class="btn btn-basic" data-toggle="modal"
				data-target=".back-warn">뒤로가기</a></li>
			<li><input type="submit" value="저장하기" class="submit-input"></li>
		</ul>
	</fieldset>
</form>
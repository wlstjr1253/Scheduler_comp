<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form action="./galleryDataAction.do" method="post"
	enctype="multipart/form-data">
	<fieldset>
		<legend>앨범 등록</legend>
		<div class="picture">
			<img src="../imgs/dummy/basic_pic.jpg" alt>
		</div>
		<div class="file">
			<label for="filename" class="btn btn-danger">파일선택</label> <input
				type="file" name="filename" id="filename" value="" required
				accept="image/*">
		</div>
		<div class="title">
			<input type="text" placeholder="사진 제목 입력" required name="g_title"
				maxlength="25">
		</div>
		<div class="diary-content">
			<textarea name="editordata" id="summernote" cols="30" rows="10"
				placeholder="일기쓰기"></textarea>
		</div>
		<ul class="btn-list">
			<li><a href="#" class="btn btn-basic" data-toggle="modal"
				data-target=".back-warn">뒤로가기</a></li>
			<li><input type="submit" value="저장하기" class="submit-input"></li>
		</ul>
	</fieldset>
</form>
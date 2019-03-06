<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(document).ready(function(){
        $('#summernote').summernote({
            placeholder: '내용을 작성해 주세요.',
            height: 300
        });
    })
</script>
<!-- main -->
<div class="main diary gallery-write">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<c:if test="${empty galleryItem}">
			<jsp:include page="gallery.write.form.jsp" />
			</c:if>
			
			<c:if test="${!empty galleryItem}">
			<jsp:include page="gallery.modify.form.jsp" />
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
				<button type="button" class="btn btn-danger" onclick="location.href='./gallery.do';">뒤로가기</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: warn-modal -->

<script src="${pageContext.request.contextPath}/js/gallery.write.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- main -->
<div class="main diary gallery">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<div class="align-right">
				<a href="./galleryWrite.do" class="btn btn-basic">갤러리 작성</a>
			</div>
			<!-- S: gallery-list -->
			<section class="gallery-list">
				<h2>갤러리</h2> <p>총 ${count} 개의 내용이 등록 되어 있습니다.</p>
				<!-- S: row -->
				<ul class="row">
					<c:if test="${empty galleryList}">
					<li class="no-result col-md-12">등록된 갤러리가 없습니다.</li>
					</c:if>
					
					<c:if test="${!empty galleryList}">
					<c:forEach var="gal" items="${galleryList}">
						<li class="col-md-3">
							<a href="./galleryDetail.do?g_idx=${gal.g_idx}">
								<figure>
									<div class="thumbnail">
										<img src="${pageContext.request.contextPath}/upload/${gal.g_photo2}">
									</div>
									<figcaption>${gal.g_title}</figcaption>
								</figure>
							</a>
						</li>
					</c:forEach>
					</c:if>
				</ul>
				<!-- E: row -->
			</section>
			<!-- E: gallery-list -->

			<c:if test="${!empty galleryList}">
			<!-- S: pagination -->
			<ul class="pagination justify-content-center">
				${pagingHtml}
			</ul>
			<!-- E: pagination -->
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
				<button type="button" class="btn btn-danger">뒤로가기</button>
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
				<button type="button" class="btn btn-danger">삭제</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: warn-modal -->
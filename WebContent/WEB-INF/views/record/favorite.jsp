<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- main -->
<div class="main diary favorite">
	<!-- S: bg -->
	<div class="bg">
		<!-- S: content -->
		<div class="content">
			<h2>즐겨찾기</h2>

			<!-- S: schedule-list -->
			<div class="schedule-list sch-list">
				<!-- S: comming-list -->
				<section class="comming-list">
					<div class="list-header">
						<h3>일정 모음</h3>
						<div class="srch-box">
							<p class="total-guide">총 ${scCount}개의 일정 모음이 있습니다.</p>
							<form>
								<input type="text" name="scKeyword">
								<input type="submit" value="검색">
							</form>
						</div>
					</div>
					<div class="ajax-loading">
						<img alt="로딩중" src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg">
					</div>
					<ul>
						<c:if test="${empty scList}">
						<li class="no-result">즐겨찾기된 일정이 없습니다.</li>
						</c:if>
						
						<c:if test="${!empty scList}">
						<c:forEach var="sch" items="${scList}">
						<li class="clearfix">
							<a href="${pageContext.request.contextPath}/sc/detailSchedule.do?sc_idx=${sch.sc_idx}"> 
								<!-- S: list-content -->
								<div class="list-content">
									<h4 class="title">${sch.sc_title}</h4>
									<p>날짜 : ${sch.sc_startDate} <c:if test="${sch.sc_startDate != sch.sc_endDate}">~  ${sch.sc_endDate}</c:if></p>
									<c:choose>
									<c:when test="${sch.sc_all_day != 'Y'}">
									<p>시간 : ${sch.sc_startTime} <c:if test="${sch.sc_startTime != sch.sc_endTime}">부터 ${sch.sc_endTime}까지</c:if></p>
									</c:when>
									<c:when test="${sch.sc_all_day == 'Y'}">
									<p>시간 : 종일</p>
									</c:when>
									</c:choose>
									<p>장소 : ${sch.sc_place}</p>
									<c:if test="${!empty sch.sc_content}">
									<p>메모 : <br>${sch.sc_content}</p>
									</c:if>
								</div> <!-- E: list-content -->
							</a> 
							<!-- S: list-control -->
							<div class="list-control">
								<a href="#" data-toggle="modal" data-target=".del-fav" data-idx="${sch.sc_idx}" data-part="schedule">
									<span class="icon"><i class="fas fa-times"></i></span>
								</a>
							</div>
							<!-- E: list-control -->
						</li>
						</c:forEach>
						</c:if>
					</ul>
				</section>
				<!-- E: comming-list -->

				<!-- S: pagination -->
				<ul class="pagination justify-content-center sc-paging">
					${scPaging}
				</ul>
				<!-- E: pagination -->
			</div>
			<!-- E: schedule-list -->

			<!-- S: schedule-list -->
			<div class="schedule-list diary-list">
				<!-- S: comming-list -->
				<section class="comming-list">
					<div class="list-header">
						<h3>다이어리 모음</h3>
						<div class="srch-box">
							<p class="total-guide">총 ${dCount}개의 다이어리 모음이 있습니다.</p>
							<form>
								<input type="text" name="diKeyword">
								<input type="submit" value="검색">
							</form>
						</div>
					</div>
					<div class="ajax-loading">
						<img alt="로딩중" src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg">
					</div>
					<ul>
						<c:if test="${empty dList}">
						<li class="no-result">즐겨찾기된 다이어리가 없습니다.</i></li>
						</c:if>
						
						<c:if test="${!empty dList}">
						<c:forEach var="d" items="${dList}">
						<li class="clearfix">
							<a href="${pageContext.request.contextPath}/record/readDiary.do?d_idx=${d.d_idx}"> 
								<!-- S: list-content -->
								<div class="list-content">
									<h4 class="title">${d.d_title}</h4>
									<p>날짜 : ${d.d_reg_date}</p>
									<p>${fn:substring(d.d_content,0,20)}</p>
								</div> 
								<!-- E: list-content -->
							</a> 
							<!-- S: list-control -->
							<div class="list-control">
								<a href="#" data-toggle="modal" data-target=".del-fav" data-idx="${d.d_idx}" data-part="diary"> 
									<span class="icon">
										<i class="fas fa-times"></i>
									</span>
								</a> 
							</div> <!-- E: list-control --></li>
						</c:forEach>
						</c:if>
					</ul>
				</section>
				<!-- E: comming-list -->

				<!-- S: pagination -->
				<ul class="pagination justify-content-center di-paging">
					${dPaging}
				</ul>
				<!-- E: pagination -->
			</div>
			<!-- E: schedule-list -->

			<!-- S: gallery -->
			<div class="gallery">
				<!-- S: gallery-list -->
				<section class="gallery-list">
					<div class="list-header">
						<h3>갤러리 모음</h3>
						<div class="srch-box">
							<p class="total-guide">총 ${galleryCount}개의 갤러리 모음이 있습니다.</p>
							<form>
								<input type="text" name="galKeyword">
								<input type="submit" value="검색">
							</form>
						</div>
					</div>
					<div class="ajax-loading">
						<img alt="로딩중" src="${pageContext.request.contextPath}/imgs/curved-bar-spinner.svg">
					</div>
					<!-- S: row -->
					<ul class="row">
						<c:if test="${empty galleryList}">
						<li class="col-md-12">
							<p class="no-result">즐겨찾기 된 앨범이 없습니다.</p>
						</li>
						</c:if>
						
						<c:if test="${!empty galleryList}">
						<c:forEach var="gallery" items="${galleryList}">
						<li class="col-md-3">
							<a href="${pageContext.request.contextPath}/record/galleryDetail.do?g_idx=${gallery.g_idx}">
								<figure>
									<div class="thumbnail">
										<img src="${pageContext.request.contextPath}/upload/${gallery.g_photo2}">
									</div>
									<figcaption class="title">${gallery.g_title}</figcaption>
								</figure>
							</a>
							<a class="btn btn-close" data-toggle="modal" data-target=".del-fav" data-idx="${gallery.g_idx}" data-part="gallery">
								<span class="icon">
									<i class="fas fa-times"></i>
								</span>
							</a>
						</li>
						</c:forEach>						
						</c:if>
					</ul>
					<!-- E: row -->
				</section>
				<!-- E: gallery-list -->
			</div>
			<!-- E: gallery -->

			<!-- S: pagination -->
			<ul class="pagination paging-btn g_paging">
				${g_pagingHtml}
			</ul>
			<!-- E: pagination -->

		</div>
		<!-- E: content -->
	</div>
	<!-- E: bg -->
</div>

<script>
Handlebars.registerHelper('isSame', function(startDate, endDate, options) {
	var startDate = startDate;
	var endDate = endDate;
	if (startDate == endDate) {
		return options.fn(this);
	} else {
		return options.inverse(this);
	}
})

Handlebars.registerHelper('getContext', function(sc_content, options){
	if (sc_content) {
		return options.fn(this);
	} else {
		return options.inverse(this);
	}
})

Handlebars.registerHelper('isAllDay', function(sc_all_day, options) {
	if (sc_all_day == 'Y') {
		return options.fn(this);
	} else {
		return options.inverse(this);
	}
})
</script>

<script id="schList" type="text/x-handlebars-template">
	{{#scList}}
	<li class="clearfix">
		<a href="detailSchedule.do?sc_idx={{sc_idx}}"> <!-- S: list-content -->
			<div class="list-content">
				<h4 class="title">{{sc_title}}</h4>
				{{#isSame sc_startDate sc_endDate}}
				<p>날짜 : {{sc_startDate}}</p>
				{{else}} 
				<p>날짜 : {{sc_startDate}} ~ {{sc_endDate}}</p>
				{{/isSame}}
					{{#isAllDay sc_all_day}}
				<p>시간 : 종일 </p>
				{{else}}
				{{#isSame sc_startTime sc_endTime}}
				<p>시간 : {{sc_startTime}}</p>
				{{else}}
				<p>시간 : {{sc_startTime}} 부터 {{sc_endTime}}까지</p>
				{{/isSame}}
				{{/isAllDay}}
					<p>장소 : {{sc_place}}</p>
				{{#getContext sc_content}}
				<p>메모 : <br>{{sc_content}}</p>
				{{/getContext}}
			</div> <!-- E: list-content -->
		</a> <!-- S: list-control -->
		<div class="list-control">
			<a href="#" data-toggle="modal" data-target=".del-fav" data-idx="{{sc_idx}}" data-part="schedule">
				<span class="icon">
					<i class="fas fa-times"></i>
				</span>
			</a>
		</div>
		<!-- E: list-control -->
	</li>
	{{else}}
	<li class="no-result">검색된 일정이 없습니다.</li>
	{{/scList}}
</script>

<script>
Handlebars.registerHelper('printContent', function(content, options) {
	if (content.length < 20) {
		return content;
	} else {
		return content.substr(0, 20);
	}
})
</script>

<script id="diaryList" type="text/x-handlebars-template">
	{{#dList}}
	<li class="clearfix">
		<a href="${pageContext.request.contextPath}/record/readDiary.do?d_idx={{d_idx}}">
			<div class="list-content">
				<h4 class="title">{{d_title}}</h4>
				<p>날짜 : {{d_reg_date}}</p>
				<p class="d-content">
				{{#printContent d_content}}
				{{printContent content}}
				{{/printContent}}
				</p>
			</div>
		</a>
		<div class="list-control">
			<a href="#" data-toggle="modal" data-target=".del-fav" data-idx="{{d_idx}}" data-part="diary"> 
				<span class="icon">
					<i class="fas fa-times"></i>
				</span>
			</a> 
		</div>
	</li>
	{{else}}
	<li class="no-result">검색된 다이어리가 없습니다.</li>
	{{/dList}}
</script>

<script id="galleryList" type="text/x-handlebars-template">
	{{#galleryList}}
	<li class="col-md-3">
	<a href="${pageContext.request.contextPath}/record/galleryDetail.do?g_idx={{g_idx}}">
		<figure>
			<div class="thumbnail">
				<img src="${pageContext.request.contextPath}/upload/{{g_photo2}}">
			</div>
			<figcaption class="title">{{g_title}}</figcaption>
		</figure>
	</a>
	<a class="btn btn-close" data-toggle="modal" data-target=".del-fav" 
		data-idx="{{g_idx}}" data-part="gallery">
		<span class="icon">
			<i class="fas fa-times"></i>
		</span>
	</a>
	</li>
	{{else}}
	<li class="col-md-12">
		<p class="no-result">검색된 결과가 없습니다.</p>
	</li>
	{{/galleryList}}
</script>


<!-- S: warn-modal -->
<div class="modal fade warn-modal del-fav" role="dialog">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<!-- S: modal-header -->
			<div class="modal-header">
				<h5 class="modal-title">즐겨찾기 제거</h5>
			</div>
			<!-- E: modal-header -->

			<!-- S: modal-body -->
			<div class="modal-body">
				<p></p>
			</div>
			<!-- E: modal-body -->

			<!-- S: modal-footer -->
			<div class="modal-footer align-center">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary del-favorite">확인</button>
			</div>
			<!-- E: modal-footer -->
		</div>
	</div>
</div>
<!-- E: warn-modal -->